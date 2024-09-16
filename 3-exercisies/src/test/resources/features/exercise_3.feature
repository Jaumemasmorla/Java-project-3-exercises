Feature: Validate Brackets Service

  Scenario Outline: Validate if the given sentence has balanced brackets
    Given I have the sentence "<sentence>"
    When I send the sentence to the validate brackets service
    Then I should receive a "<response>" response OK or KO

    Examples:
      | sentence                | response |
      | aaa                     | OK       |
      | a(a)a                   | OK       |
      | (agfdgdsf)              | OK       |
      | (agf(dg)d()sf)         | OK       |
      | (agf)dg(dsf)           | OK       |
      | agf)dg(dsf)            | KO       |
      | )agf(dg(dsf)           | KO       |
      | a(gf()dg(ds)f          | KO       |
