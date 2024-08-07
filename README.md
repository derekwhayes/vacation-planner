# Derek Hayes - Student ID: 000865954
## WGU D308 – MOBILE APPLICATION DEVELOPMENT (ANDROID)
## Vacation Planner
The application allows the user to create and manage vacations. The application provides the means to create, edit, and delete vacations and excursions for the vacations. They are able to setup alerts for vacation start and end date as well as excursion dates. Users are also able to share all the vacation details via sms/email/etc.

### Android Version - 14
### API Version - 34
 
### <a href="https://gitlab.com/wgu-gitlab-environment/student-repos/dhaye48/d308-mobile-application-development-android.git">Vacation Planner GitLab Repository</a>

## Directions
* <strong>B1</strong> - Persist data

    1. From the home activity touch the 'Get Started' button. This leads to the Vacation List activity. If the user has entered any vacations at this point they will populate here and provide evidence that the application is providing persistent storage.

* <strong>B2</strong> - Add a new vacation

    1. From the home activity touch the 'Get Started' button.
    2. Touch the '+' FAB in the lower right hand corner.
    3. Enter vacation details in provided fields.
    4. Select dates using buttons and datepicker. (Be sure the start date is before the end date)
    5. Touch the save icon in the upper right hand corner.

* <strong>B3</strong>
    
    A. View detailed Vacation information
        
    1. From the home activity touch the 'Get Started' button.
    2. Touch the vacation you wish to see a detailed view.
    3. From here you may update vacation information as desired.
    
    B. 
    
    * Update vacation

        1. From the home activity touch the 'Get Started' button.
        2. Touch the vacation you wish to edit.
        3. Touch the pencil icon in the upper right hand corner.
        4. Edit the vacation details as desired.
        5. Touch the save icon in the upper right hand corner.

    * Delete vacation

        1. From the home activity touch the 'Get Started' button.
        2. Touch the vacation you wish to delete.
        3. Depending on the width of the screen either touch the trash can icon or select the three dot overflow menu and 'Delete' in the upper right hand corner.
        4. Touch 'OK' on the confirmation dialog.

    C. Date validation

    The application uses the datepicker dialog to ensure correct date formatting.

    D. Correct vacation start and end dates validation

    The application will not allow the user to save a vacation that has a start date after the end date. An error message is displayed.

    E. Setup vacation alert

    1. From the home activity touch the 'Get Started' button.
    2. Touch the vacation you wish to set an alert.
    3. Depending on the width of the screen either touch the alarm clock icon or select the three dot overflow menu and 'Notify' in the upper right hand corner.
    4. Select either the start date or end date alert.
        A toast message will display on the chosen date. A notification will also display if permissions have been granted.
    
    F. Share vacation details

    1. From the home activity touch the 'Get Started' button.
    2. Touch the vacation you wish to share.
    3. Touch the share icon.
    4. Select how you would like to share and follow that method to share the information.

    G. View excursion list

    1. From the home activity touch the 'Get Started' button.
    2. Touch the vacation you wish to view excursions from.
    3. The excursion are displayed in a list in this detailed vacation view.

    H.
    * Add excursion
       
        1. From the home activity touch the 'Get Started' button.
        2. Touch the vacation you wish to add an excursion to.
        3. Touch the '+' FAB in the lower right hand corner.
        4. Enter excursion details in provided fields.
        5. Select excursion date using the button. (Be sure the excursion date is within the vacation start and end dates [inclusive])
        6. Touch the save icon in the upper right hand corner.

    * Update excursion

        1. From the home activity touch the 'Get Started' button.
        2. Touch the vacation you wish to add an excursion to.
        3. Touch the excursion you wish to edit.
        4. Touch the pencil icon in the upper right hand corner.
        5. Edit the excursion details as desired.
        6. Touch the save icon in the upper right hand corner.

    * Delete excursion

        1. From the home activity touch the 'Get Started' button.
        2. Touch the vacation you wish to delete the excursion from.
        3. Touch the excursion you wish to delete.
        4. Touch the trash can icon in the upper right hand corner.
        5. Touch 'OK' on the confirmation dialog.

* <strong>B4</strong> View excursion details

    1. From the home activity touch the 'Get Started' button.
    2. Touch the vacation you wish to view excursion details from.
    3. Touch excursion you wish to view details from.