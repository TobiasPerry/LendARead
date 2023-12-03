package ar.edu.itba.paw.webapp.auth.voters;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
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
public class ChangeAssetStatusVoter implements AccessDecisionVoter<FilterInvocation> {
    private final AssetInstanceService assetInstanceService;

    @Autowired
    public ChangeAssetStatusVoter(AssetInstanceService assetInstanceService) {
        this.assetInstanceService = assetInstanceService;
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
        vote.set(ACCESS_ABSTAIN);
        if(filterInvocation.getRequestUrl().toLowerCase().contains("/changestatus/") || filterInvocation.getRequestUrl().toLowerCase().contains("/mybookdetails/") || filterInvocation.getRequestUrl().toLowerCase().contains("/editasset/") ) {

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
            try {
                if (assetInstanceService.isOwner(id, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()))
                    vote.set(ACCESS_GRANTED);
                else {
                    vote.set(ACCESS_DENIED);
                }
            }catch (AssetInstanceNotFoundException e){
                vote.set(ACCESS_DENIED);
            }
        }

        return vote.get();
    }
}
