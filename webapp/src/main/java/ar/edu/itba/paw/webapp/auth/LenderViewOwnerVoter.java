package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LenderViewOwnerVoter implements AccessDecisionVoter<FilterInvocation> {
    private final UserAssetInstanceService userAssetInstanceService;

    @Autowired
    public LenderViewOwnerVoter(UserAssetInstanceService userAssetInstanceService) {
        this.userAssetInstanceService = userAssetInstanceService;
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
            if (url.contains("/lentbookdetails/") || url.contains("/returnasset") || url.contains("/rejectasset") || url.contains("/confirmasset")) {
                StringBuilder stringBuilder = new StringBuilder(filterInvocation.getRequestUrl());
                stringBuilder.delete(0, stringBuilder.lastIndexOf("/") + 1);
                int variables = stringBuilder.indexOf("?");
                if(variables != -1)
                    stringBuilder.delete(variables,stringBuilder.length()+1);
                if (userAssetInstanceService.getBorrowedAssetInstance(Integer.parseInt(stringBuilder.toString())).getOwner().getEmail().equals(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()))
                    vote.set(ACCESS_GRANTED);
                else {
                    vote.set(ACCESS_DENIED);
                }
            }


        return vote.get();
    }
}
