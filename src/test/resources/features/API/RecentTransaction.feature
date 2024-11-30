Feature:

  Scenario:
    Given url 'http://localhost:3001/v1/recent/create'
    And request {"accessTimestamp": "2024-11-11","transactionId": 3}
    When method post
    Then status 200

    # Optional cleanup step
    Given url 'http://localhost:3001/v1/recent/3'
    When method delete
    Then status 200