# adyen-report-merger
The Adyen Report Merger Tool download a range of reports from the Adyen Backoffice with option to merge into one file

This tool allows you, through an user interface, to choose a sequence of already available reports on your Merchant Account and download and/or merge them in just one file.

## Requirements
* Java 1.7 is required in order to run this application. Please update your Java by going to http://java.com/getjava if you have a lower version.
* Include commons-codec see https://commons.apache.org/proper/commons-codec/  
* Include commons-logging see https://commons.apache.org/proper/commons-logging/download_logging.cgi


## Basic usage instructions:

1. Run project
	- `./gradlew build && ./gradlew run`
	
2. Select the report you would like to get/merge in the combo box 'Report type'. This tool for now is just available for these reports: 
	
	- Settlement detail report
	- Received payments report
	- Payments accounting reports

3. Select the way you would like to use to choose the reports range:
	
	- By batch: this option will show two combo boxes with every available reports, letting you choose a range.
	- By date: this option will show two calendars you can choose the dates generation range from.

4. Introduce the credentials that are configured for your report user in the Adyen Customer Area. You can check it following these steps:
	
	Log in https://ca-live.adyen.com >> Settings >> Users >> report user
	
	Note that you just have to insert the 'report user' till the '@' value, since the tool already detect the rest.
	
5. Choose the folder where you would like to place the generated reports clicking on the 'Choose path to save the reports' button.

6. Click on 'Continue' button.

7. Depending on the selected option in step 3 you would get a different screen:

    - By batch: this option will show two combo boxes with every available reports, letting you choose a range. The 'Start batch' will be the first generated one, and the 'End batch' the last generated one. 
	
	- By date: this option will show two calendars. From these calendars you can choose the dates generation range you would like to get the reports. 
	In the right list, you can see the reports are contained in the selected period.
	Note you can also clear these dates to don't limit one or anyone of the date limits.
	
8. You can choose in each one of these screens if you want to just download the reports or if you also want to merge them into one.

9. Click on 'Confirm' button.

10. A successful screen should appear specifying the folder where the reports should be placed. 

	Note in this screen you can go back to the initial screen clicking in 'Start again' button, being not needed to insert again the credentials.

11. To open the file, if you choose Libre Office Calc or MS Excel, select just these options:
	- Choose Character set: Western Europe (ISO-8859-15/EURO)
	- Choose language: English
	- Select as separator options just: Comma
	- Other options:  Quoted special numbers
	- Fields: Make pspreference, mercantreference and modificationreference columns type 'Text', since it is a number and the 'Standard' will round this big number.

Errors:
- If you get an Error screen, it should shows the reason why the error has been generated and the solution to it. If it is not the case, please, contact support@adyen.com.