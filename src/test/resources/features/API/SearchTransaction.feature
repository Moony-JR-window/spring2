Feature:

  Scenario:
    Given url 'http://localhost:3001/v1/search/transaction'
    And request {"accountId": 2,"minAmount": 0,"maxAmount": 20000,"startDate": "2020-01-20","endDate": "2024-11-20"}
    When method post
    Then status 200