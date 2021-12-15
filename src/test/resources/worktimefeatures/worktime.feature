Feature: Work Time

  Scenario: You want to create a booking
    Given You have a valid BookingCreation object
    When You create the booking
    Then the inserted id should be returned