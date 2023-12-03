package ar.edu.itba.paw.webapp.auth.voters;


import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BorrowerViewVoter implements AccessDecisionVoter<FilterInvocation> {
    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;
    @Autowired
    public BorrowerViewVoter(final UserAssetInstanceService userAssetInstanceService,final UserService userService) {
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


    @SneakyThrows
    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        AtomicInteger vote = new AtomicInteger();
        String url = filterInvocation.getRequestUrl().toLowerCase();
        vote.set(ACCESS_ABSTAIN);
            if(url.contains("/borrowedbookdetails/") || url.contains("/cancelasset/")) {
                StringBuilder stringBuilder = new StringBuilder(filterInvocation.getRequestUrl());
                stringBuilder.delete(0, stringBuilder.lastIndexOf("/") + 1);
                int variables = stringBuilder.indexOf("?");
                if(variables != -1)
                    stringBuilder.delete(variables,stringBuilder.length()+1);
                int id;
                try {
                    id = Integer.parseInt(stringBuilder.toString());
                }catch (NumberFormatException e){
                    vote.set(ACCESS_DENIED);
                    return vote.get();
                }
                if (userAssetInstanceService.getBorrowedAssetInstance(id).getUserReference().getEmail().equals(userService.getCurrentUser()))
                    vote.set(ACCESS_GRANTED);
                else {
                    vote.set(ACCESS_DENIED);
                }
            }
        return vote.get();
    }
}

