package com.nzefler.user_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementApplication {

//	@Bean
//	public graphql.schema.GraphQLScalarType extendedScalarLong(){
//		return graphql.scalars.ExtendedScalars.GraphQLLong;
//	}

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
