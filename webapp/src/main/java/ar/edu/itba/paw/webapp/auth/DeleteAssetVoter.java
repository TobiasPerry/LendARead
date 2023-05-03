package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
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
public class DeleteAssetVoter implements AccessDecisionVoter<FilterInvocation> {
    @Autowired
    AssetInstanceService assetInstanceService;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        AtomicInteger vote = new AtomicInteger();
        vote.set(ACCESS_ABSTAIN);
        if(filterInvocation.getRequestUrl().toLowerCase().contains("/deleteasset/")) {

            StringBuilder stringBuilder = new StringBuilder(filterInvocation.getRequestUrl());
            stringBuilder.delete(0, stringBuilder.lastIndexOf("/") + 1);
            if(assetInstanceService.isOwner(Integer.parseInt(stringBuilder.toString()),((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()))
                vote.set(ACCESS_GRANTED);
            else {
                SecurityContextHolder.clearContext();
                vote.set(ACCESS_DENIED);
            }
        }

        return vote.get();
    }
}