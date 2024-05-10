**WORKFLOW**

1. **MAIN ENTRY**: This is the main entry page of the Hotel Reservation System. A customer can click on the Book Room option at the kiosk where as the Admin can click on the Admin Login button to get access to more options. 

   ![A screenshot of a logo

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.001.png)

   1. <a name="bookrooms"></a>**BOOK ROOMS**: Clicking the Book Room button on the main screen brings Book Rooms Page. Here the customer/admin can select the number of guests looking for rooms and the number of days for the stay. The minimum number of rooms is a text field that is automatically updated by the system based on number of guests. But the user can book same or more rooms than suggested number but not less. Then the user selects the date of check-in and the check-out date is calculated internally based on the number of days of stay. The user can then click the “SELECT ROOMS” button to proceed selecting their rooms for the stay. 

      ![A screenshot of a hotel registration form

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.002.png)

      1. **SELECT ROOMS**: This page shows the number of rooms to be booked decided on the previous page. Every room the user books may or may not change this number based on some rules discussed below. The user must select the number of rooms they selected on the previous page. It shows a list of rooms available. The user can select the room from the table and click the ADD>> button to add that room to the list and remove it form the selected table view by selecting a room and clicking the <<Remove button. Once the number of rooms to be selected reaches 0 and systems does it’s necessary validations the user can click the BOOK button to proceed to the “GUEST DETAILS” page. 

**Rules**: 

1) Every room will have a occupancy attribute associated with it. This number will be either 2 or 4. Meaning that a room can host 2 or 4 guests at max. 
1) Single Room: occupancy of 2 hosting 2 guests at max. 
1) Double Room: occupancy of 4 hosting 4 guests at max. 
1) Delux Room: occupancy of 4 hosting 4 guests at max. More space than Double room hence more expensive. 
1) Pent House: occupancy of 4 hosting 4 guests at max. Most luxurious and expensive offering of the resort. 

**Example**: there are 5 guests for which the minimum number of rooms required is 2. A double room(4/4 occupied) and a single room(1/2 occupied) . The user can increase this number in the Book Rooms page but then user will have to select that exact number of rooms on the Select Rooms Page. This means that user can increase the number of rooms to 3 and select 3 single rooms (2/2 occupancy for 2 rooms and 1/2 occupancy for 1 room). Or they user can select 5 rooms and select any rooms options available if every single guest needs to have a private room. 

![A screenshot of a hotel registration

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.003.png)

1. **GUEST DETAILS**: This page is a form to collect the necessary information for the guest making the booking. It takes information- First Name, Last Name, Address, Phone and Email. The user can then click the Save Guest button that kicks in a email check in the system (using regex to validate the email.). If the email is invalid an error is displayed otherwise the booking is confirmed and a success message is displayed. 

![A screenshot of a guest registration form

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.004.png)

1. **ADMIN LOGIN**: If the user had clicked on the Admin Login page from the main screen they are brought to this page. It takes a username and password for the admin and authenticates the user. The user can click LOGIN button to enter the admin menu. 

   ![A screenshot of a login screen

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.005.png)

   1. **ADMIN MENU**: If user is successfully authenticated, they are taken to this page. It gives user 5 options- Book Room, Bill Service, Current Bookings, Available Rooms, Exit. 

      ![A screenshot of a hotel menu

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.006.png)

      1. [**BOOK ROOM**](#bookrooms): refer to [1.1](#bookrooms) as same functionality and views take care of this option. 

      1. **BILL SERVICE**: If user click on the Bill Service they are brought to this page. It allows user to search a particular reservation using booking id. If not find an error is displayed otherwise all fields are updated with details form the found booking. Then the admin can add discount (5%, 10% or 25%) which will change the total/ night saving the info. The user can clear and or close the window as well from the corresponding buttons. 

         ![A screenshot of a hotel registration form

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.007.png)

      1. **Current Bookings**: If the user clicks the current bookings button on the admin view page they are brought to this page. It displays the number of bookings and a table view of all the bookings made along with details like name of guest, guest name, number of rooms, date and number of days. 

![A screenshot of a computer

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.008.png)

1. **Available Rooms**: If the user clicks the available rooms button on the admin view page they are brought to this page. It displays the number of available for booking and a table view of all the available rooms along with details like room id, room type and rate. ![A screenshot of a computer

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.009.png)

1. **View Guests**: Allows admin to view and search a list of all current and previous Guests in the resort. 

![A screenshot of a computer

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.010.png)






**CLASS DIAGRAM**

![A screenshot of a computer

Description automatically generated](Aspose.Words.b7ed4ca5-1ebd-4063-8b0c-00c2a2e6dcaf.011.jpeg)

