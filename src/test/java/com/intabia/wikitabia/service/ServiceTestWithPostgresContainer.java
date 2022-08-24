package com.intabia.wikitabia.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("service-integration-test")
@Sql("/init.sql")
public abstract class ServiceTestWithPostgresContainer {
  public abstract void cleanDB();
}
