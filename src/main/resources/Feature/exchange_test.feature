Feature: Testing an Exchange Rates API

  Scenario: Checking service availability
    Given Rates API for Latest Foreign Exchange rates
    When The API is available
    Then An automated test suite should run which will assert the success status of the response

  Scenario: Checking response assertion
    Given Rates API for Latest Foreign Exchange rates
    When The API is available
    Then An automated test suite should run which will assert the response

  Scenario: Checking response assertion for incorrect request
    Given Rates API for Latest Foreign Exchange rates
    When An incorrect or incomplete url is provided
    Then Test case should assert the correct response supplied by the call

  Scenario: Checking service availability for specific date
    Given Rates API for Specific date Foreign Exchange rates
    When The API is available
    Then An automated test suite should run which will assert the success status of the response

  Scenario: Checking response assertion for specific date
    Given Rates API for Specific date Foreign Exchange rates
    When The API is available
    Then An automated test suite should run which will assert the response

  Scenario: Checking response assertion for specific date for incorrect request
    Given Rates API for Specific date Foreign Exchange rates
    When A future date is provided in the url
    Then An automated test suite should run which will validate that the response matches for the current date