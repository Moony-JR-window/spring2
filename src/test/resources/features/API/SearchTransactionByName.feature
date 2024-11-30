Feature:

  Scenario:
    Given url 'http://localhost:3001/v1/search/transaction/name'
    And request {"accountId": 2,"transactionName": "payment"}
    When method post
    Then status 200