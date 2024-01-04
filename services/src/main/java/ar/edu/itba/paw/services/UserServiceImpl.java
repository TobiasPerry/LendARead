package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@EnableScheduling
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final ImagesDao imagesDao;

    private final EmailService emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    private static final String BORROWER_ROLE = "ROLE_BORROWER";


    @Autowired
    public UserServiceImpl(final PasswordEncoder passwordEncoder, final UserDao userDao, final EmailService emailService, final ImagesDao imagesDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.emailService = emailService;
        this.imagesDao = imagesDao;
    }


    @Transactional(readOnly = true)
    @Override
    public User getUser(final String email) throws UserNotFoundException {
        Optional<User> user = userDao.getUser(email);
        if (!user.isPresent()) {
            LOGGER.error("Failed to get user {}", email);
            throw new UserNotFoundException(HttpStatusCodes.NOT_FOUND);
        }
        return user.get();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(int id) throws UserNotFoundException {
        Optional<User> user = userDao.getUser(id);
        if (!user.isPresent()) {
            LOGGER.error("User with id {} not found", id);
            throw new UserNotFoundException(HttpStatusCodes.NOT_FOUND);
        }

        return user.get();
    }

    @Transactional
    @Override
    public User createUser(final String email, String name, final String telephone, final String password) {
        return userDao.addUser(Behaviour.BORROWER, email, name, telephone, passwordEncoder.encode(password));
    }

    @Transactional
    public void changeRole(final User user, final Behaviour behaviour) throws UserNotFoundException {
        boolean changed = userDao.changeRole(user.getEmail(), behaviour);
        if (!changed)
            throw new UserNotFoundException(HttpStatusCodes.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HashSet<GrantedAuthority> actualAuthorities = new HashSet<>();
        actualAuthorities.add(new SimpleGrantedAuthority("ROLE_" + behaviour.toString()));
        Authentication newAuth = new
                UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean getCurrentUserIsBorrower() {
        return getCurrentRoles().contains(new SimpleGrantedAuthority(BORROWER_ROLE));
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() throws UserNotFoundException {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getUser(userDetails.getUsername());
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getCurrentRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @Transactional
    @Override
    public void createChangePasswordToken(final String email) throws UserNotFoundException {
        String token = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Optional<User> user = userDao.getUser(email);
        if (!user.isPresent()) {
            LOGGER.error("User not found");
            throw new UserNotFoundException(HttpStatusCodes.BAD_REQUEST);
        }
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user.get().getId(), LocalDate.now().plusDays(1));
        userDao.deletePasswordRestToken(user.get().getId());
        emailService.sendForgotPasswordEmail(user.get().getEmail(), passwordResetToken.getToken(), new Locale(user.get().getLocale()));
        userDao.setForgotPasswordToken(passwordResetToken);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean isTokenValid(final int userId,final String token) {

        Optional<PasswordResetToken> passwordResetToken = userDao.getPasswordRestToken(token);

        return passwordResetToken.map(resetToken -> resetToken.getExpiryDate().isAfter(LocalDate.now())).orElse(false) && passwordResetToken.get().getUserId() == userId;
    }

    @Transactional(readOnly = true)
    @Override
    public String getUserResetPasswordToken(String email) throws UserNotFoundException {
        User user = this.getUser(email);
        Optional<PasswordResetToken> passwordResetToken = userDao.getPasswordRestTokenOfUser(user.getId());
        return passwordResetToken.map(PasswordResetToken::getToken).orElse(null);
    }


    @Override
    @Transactional
    public int changeUserProfilePic(final int id, byte[] parsedImage) throws UserNotFoundException {
        Optional<User> maybeUser = userDao.getUser(id);
        if (!maybeUser.isPresent()) {
            LOGGER.error("User not found");
            throw new UserNotFoundException(HttpStatusCodes.BAD_REQUEST);
        }

        Image image = this.imagesDao.addPhoto(parsedImage);
        LOGGER.debug("New profile image created for user email {}", maybeUser.get().getEmail());
        User user = maybeUser.get();
        user.setProfilePhoto(image);
        LOGGER.debug("User {} changed it profile picture with photo_id {}", user.getEmail(), image.getId());
        return image.getId();
    }

    @Override
    @Transactional
    public void updateUser(final int id, final String username, final String telephone,final String role,final String password) throws UserNotFoundException {
        Optional<User> maybeUser = userDao.getUser(id);
        if (!maybeUser.isPresent()) {
            LOGGER.error("User not found");
            throw new UserNotFoundException(HttpStatusCodes.BAD_REQUEST);
        }
        User user = maybeUser.get();
        if (username != null) {
            user.setName(username);
        }
        if (telephone != null) {
            user.setTelephone(telephone);
        }
        if (role != null) {
            this.changeRole(user,Behaviour.valueOf(role));
        }
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

    }

    @Transactional
    @Override
    public void deleteToken(String token) {
        userDao.deletePasswordRestToken(token);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deletePastChangePasswordTokens() {
        userDao.deletePasswordRecoveryTokensOnDay(LocalDate.now());
    }
}
