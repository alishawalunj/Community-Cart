package com.nzefler.community_service.exception;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex instanceof GraphQLApiException){
            status = ((GraphQLApiException) ex).getStatus();
        }

        return GraphQLError.newError().message(ex.getMessage()).extensions(Map.of("code",status.value()))
                .path(env.getExecutionStepInfo().getPath()).location(env.getExecutionStepInfo().getFieldDefinition().getDefinition().getSourceLocation())
                .build();
    }
}
