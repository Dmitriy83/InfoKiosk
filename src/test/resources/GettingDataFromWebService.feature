Feature: Getting data from web-service

  Scenario: Input wrong wsdl
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
