# WalmartQA
Walmart QA Exercise

Getting Started:

Pre-requisites- 
  The environment must contain git, maven, firefox and java in order to run the tests successfully

Installation from commandline -
  1. Create a directory to store project
  2. Clone from git-hub: git clone https://github.com/vignesh1rajan/HomeAway.git
  3. CD into the directory from above and pull dependencies and run tests
      mvn clean install -DsuiteXmlFile=RegressionTest.xml

Running from IDE-
  1. Import project as a maven project
  2. Get TestNG if it is not already installed
  3. Create a TestNG configuration by "SuiteName" and choose any of .xml tests from "src/test/resources/" folder.
  (RegressionTest.xml contains all the tests)
  Available tests suites are - APITest.xml, LoginOrderTest.xml, RegressionTest.xml
  Test Reports are available at - \target\surefire-reports 
Notes: In order to keeps tests from failing move curser away from browser area (task bar).

Issues:
There are many instances where locators "classNames" where used in order to find objects. This is very unreliable and hard to maintain. Best practices include having a unique id's for interactive objects.
The fluentWait for tests were set at 10 seconds and would fail if objects do not load with the configured times. Several tests did fail to load within the configured wait times.
Bug was found during test where if a item was removed from the cart and added again the item quantity would increase instead for starting from zero.

I was unable to get to correct Cookie configuration for the API - view cart test. Even after using jssessionID and WMSessionID the transaction would reset.
Without proper documentation, I was also unable to find the use for the getToken method. The call was initialized however never used in the subsequent calls.

Also there were inconsistencies with the URI structures - the initial call to login is api.mobile.walmart.com while subsequent calls were https://mobile.walmart.com

There were lots of redundant infomation from the get item calls like department metadata that made the call return 100+ of datafields 
which would slow down the calls. 
Proper GET, POST methods were not used accourding to REST standards.

Tools:

  https://git-scm.com/downloads
  https://maven.apache.org/download.cgi
  https://maven.apache.org/install.html
  https://www.mozilla.org/en-US/firefox/all/
