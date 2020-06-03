Feature: Print pay slips

   Scenario: Inputting wrong wsdl
    When I input "wrong.wsdl" in field "txtWSDLAddress"
    And I input "77-77" in field "txtSupportPhone"
    And I click button "btnStartInfoKiosk"
    And I wait 2 seconds
    Then Frame "InfoKiosk" displayed
    Then I see text "Обратитесь в тех. поддержку по телефону 77-77."
    And I click button "btnCloseSession"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnCloseSession"
    Then I see text "ОРГАНИЗАЦИЯ НЕ ОПРЕДЕЛЕНА."

  Scenario: Using wrong key card
    When I input "http://localhost/zup/ws/infokiosk.1cws?wsdl" in field "txtWSDLAddress"
    And I input "77-77" in field "txtSupportPhone"
    And I input "AdminWS" in field "txtLogin"
    And I input "123qweASD" in field "pswPassword"
    And I click button "btnStartInfoKiosk"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnStartInfoKiosk"
    And I wait 2 seconds
    Then Frame "InfoKiosk" displayed
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    And I press button with key 10 on keyboard
    And I wait 2 seconds
    Then I see text "Обратитесь в тех. поддержку по телефону 77-77."
    And I click button "btnCloseSession"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnCloseSession"
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    Then I don't see text "ОРГАНИЗАЦИЯ НЕ ОПРЕДЕЛЕНА."

  Scenario: Using right key card and change the month
    When I input "http://localhost/zup/ws/infokiosk.1cws?wsdl" in field "txtWSDLAddress"
    And I input "77-77" in field "txtSupportPhone"
    And I input "AdminWS" in field "txtLogin"
    And I input "123qweASD" in field "pswPassword"
    And I click button "btnStartInfoKiosk"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnStartInfoKiosk"
    And I wait 2 seconds
    Then Frame "InfoKiosk" displayed
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    And I press button with key 49 on keyboard
    And I wait 2 seconds
    And I press button with key 10 on keyboard
    And I wait 2 seconds
    Then I see text "таб. номер"
    And I click button "btnMonthIncrease"
    And I click button "btnMonthIncrease"
    And I click button "btnMonthIncrease"
    And I click button "brnMonthDecrease"
    And I click button "btnMonthIncrease"
    And I click button "brnMonthDecrease"
    And I click button "btnCloseSession"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnCloseSession"
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    Then I don't see text "ОРГАНИЗАЦИЯ НЕ ОПРЕДЕЛЕНА."

    # Повторим тест дважы, т.к. ранее была выявлена и исправлена ошибка зависания начального
    # экрана после нажатий кнопок изменения месяца
    And I press button with key 49 on keyboard
    And I wait 2 seconds
    And I press button with key 10 on keyboard
    And I wait 2 seconds
    Then I see text "таб. номер"
    And I click button "btnMonthIncrease"
    And I click button "btnMonthIncrease"
    And I click button "btnMonthIncrease"
    And I click button "brnMonthDecrease"
    And I click button "btnMonthIncrease"
    And I click button "brnMonthDecrease"
    And I click button "btnCloseSession"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnCloseSession"
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    Then I don't see text "ОРГАНИЗАЦИЯ НЕ ОПРЕДЕЛЕНА."

  Scenario: Checking timeout
    When I input "http://localhost/zup/ws/infokiosk.1cws?wsdl" in field "txtWSDLAddress"
    And I input "77-77" in field "txtSupportPhone"
    And I input "AdminWS" in field "txtLogin"
    And I input "123qweASD" in field "pswPassword"
    And I click button "btnStartInfoKiosk"
    # Из-за глюка в первый раз промахивается мимо кнопки. Во второй раз попадает.
    And I click button "btnStartInfoKiosk"
    And I wait 2 seconds
    Then Frame "InfoKiosk" displayed
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    And I press button with key 49 on keyboard
    And I wait 2 seconds
    And I press button with key 10 on keyboard
    And I wait 2 seconds
    Then I see text "таб. номер"
    And I wait 16 seconds
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    And I press button with key 10 on keyboard
    Then I see text "Обратитесь в тех. поддержку по телефону 77-77."
    And I wait 16 seconds
    Then I see text "ДОБРО ПОЖАЛОВАТЬ НА ПОРТАЛ"
    And I press button with key 49 on keyboard
    And I wait 2 seconds
    And I press button with key 10 on keyboard
    And I wait 2 seconds
    Then I see text "таб. номер"



