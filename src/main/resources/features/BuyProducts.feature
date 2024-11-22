Feature: Buying products

  @buy-product
  Scenario Outline: Buying evening dresses in automationpractice.com
    Given go to the website automation
    When the user sign in on page
    And buying "<quantity>" product
    Then order is registered

    Examples:
      | quantity |
      |  1       |