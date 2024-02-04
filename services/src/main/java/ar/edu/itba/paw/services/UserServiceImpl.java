package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.ImageNotExistException;
import ar.edu.itba.paw.exceptions.UnableToChangeRoleException;
import ar.edu.itba.paw.exceptions.UnableToCreateTokenException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.User;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final ImagesDao imagesDao;

    private final EmailService emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);


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
       return userDao.getUser(email).orElseThrow(() -> {
           LOGGER.error("User with email {} not found", email);
           return new UserNotFoundException();
       });
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(int id) throws UserNotFoundException {
        return userDao.getUser(id).orElseThrow(() -> {
            LOGGER.error("User with email {} not found", id);
            return new UserNotFoundException();
        });
    }

    @Transactional
    @Override
    public User createUser(final String email, String name, final String telephone, final String password) {
        return userDao.addUser(Behaviour.BORROWER, email, name, telephone, passwordEncoder.encode(password), LocaleContextHolder.getLocale().getLanguage());
    }

    @Transactional
    public void changeRole(final User user, final Behaviour behaviour) throws  UnableToChangeRoleException {
        if (user.getBehavior().equals(Behaviour.LENDER) && behaviour.equals(Behaviour.BORROWER)) {
            throw new UnableToChangeRoleException();
        }
        user.setBehavior(behaviour);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HashSet<GrantedAuthority> actualAuthorities = new HashSet<>();
        actualAuthorities.add(new SimpleGrantedAuthority("ROLE_" + behaviour.toString()));
        Authentication newAuth = new
                UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
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


    @Transactional
    @Override
    public void createChangePasswordToken(final String email) throws  UnableToCreateTokenException {
        String token = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        User user = userDao.getUser(email).orElseThrow(UnableToCreateTokenException::new);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user.getId(), LocalDate.now().plusDays(1));
        userDao.deletePasswordRestToken(user.getId());
        emailService.sendForgotPasswordEmail(user.getEmail(), passwordResetToken.getToken(), new Locale(user.getLocale()));
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
        User user = userDao.getUser(id).orElseThrow(() -> {
            LOGGER.error("User not found");
            return new UserNotFoundException();
        });
        Image image = this.imagesDao.addPhoto(parsedImage);
        LOGGER.debug("New profile image created for user email {}", user.getEmail());
        user.setProfilePhoto(image);
        LOGGER.debug("User {} changed it profile picture with photo_id {}", user.getEmail(), image.getId());
        return image.getId();
    }

    @Override
    @Transactional
    public void updateUser(final int id, final String username, final String telephone,final String role,final String password,final Integer imageId) throws UserNotFoundException, UnableToChangeRoleException, ImageNotExistException {
        Optional<User> maybeUser = userDao.getUser(id);

        User user = maybeUser.orElseThrow(() -> {
            LOGGER.error("User not found");
            return new UserNotFoundException();
        });
        if (imageId != null) {
            Image image = imagesDao.getImage(imageId).orElseThrow(() -> {
                LOGGER.error("Image not found");
                return new ImageNotExistException();
            });
            user.setProfilePhoto(image);
        }
        if (role != null) {
            this.changeRole(user,Behaviour.valueOf(role));
        }
        if (username != null) {
            user.setName(username);
        }
        if (telephone != null) {
            user.setTelephone(telephone);
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
