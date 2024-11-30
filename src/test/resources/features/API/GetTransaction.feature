Feature: Account Creation

  Scenario:
    Given url 'http://localhost:3001/v1/recent'
    And request {"accountId": 1,"page": 0,"limit": 0}
    When method post
    Then status 200

