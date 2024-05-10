**WORKFLOW**

1. **MAIN ENTRY**: This is the main entry page of the Hotel Reservation System. A customer can click on the Book Room option at the kiosk where as the Admin can click on the Admin Login button to get access to more options. 

   ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/4fff461a-e6ae-44dd-9ffc-0a3535b67319)


   1. <a name="bookrooms"></a>**BOOK ROOMS**: Clicking the Book Room button on the main screen brings Book Rooms Page. Here the customer/admin can select the number of guests looking for rooms and the number of days for the stay. The minimum number of rooms is a text field that is automatically updated by the system based on number of guests. But the user can book same or more rooms than suggested number but not less. Then the user selects the date of check-in and the check-out date is calculated internally based on the number of days of stay. The user can then click the “SELECT ROOMS” button to proceed selecting their rooms for the stay. 

      ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/c2cd3ee4-90e4-4030-a77e-8d4563da6125)


      1. **SELECT ROOMS**: This page shows the number of rooms to be booked decided on the previous page. Every room the user books may or may not change this number based on some rules discussed below. The user must select the number of rooms they selected on the previous page. It shows a list of rooms available. The user can select the room from the table and click the ADD>> button to add that room to the list and remove it form the selected table view by selecting a room and clicking the <<Remove button. Once the number of rooms to be selected reaches 0 and systems does it’s necessary validations the user can click the BOOK button to proceed to the “GUEST DETAILS” page. 

         **Rules**: 

         1) Every room will have a occupancy attribute associated with it. This number will be either 2 or 4. Meaning that a room can host 2 or 4 guests at max. 
         1) Single Room: occupancy of 2 hosting 2 guests at max. 
         1) Double Room: occupancy of 4 hosting 4 guests at max. 
         1) Delux Room: occupancy of 4 hosting 4 guests at max. More space than Double room hence more expensive. 
         1) Pent House: occupancy of 4 hosting 4 guests at max. Most luxurious and expensive offering of the resort. 

         **Example**: there are 5 guests for which the minimum number of rooms required is 2. A double room(4/4 occupied) and a single room(1/2 occupied) . The user can increase this number in the Book Rooms page but then user will have to select that exact number of rooms on the Select Rooms Page. This means that user can increase the number of rooms to 3 and select 3 single rooms (2/2 occupancy for 2 rooms and 1/2 occupancy for 1 room). Or they user can select 5 rooms and select any rooms options available if every single guest needs to have a private room. 

         ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/388985c2-0a99-4162-9634-8bbf6a444f2a)


            1. **GUEST DETAILS**: This page is a form to collect the necessary information for the guest making the booking. It takes information- First Name, Last Name, Address, Phone and Email. The user can then click the Save Guest button that kicks in a email check in the system (using regex to validate the email.). If the email is invalid an error is displayed otherwise the booking is confirmed and a success message is displayed. 

            ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/ed53617e-84d9-449d-987a-da2deebcb3b8)


1. **ADMIN LOGIN**: If the user had clicked on the Admin Login page from the main screen they are brought to this page. It takes a username and password for the admin and authenticates the user. The user can click LOGIN button to enter the admin menu. 

   ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/2487aedb-b40a-485c-8a97-431ec605ac7e)

   1. **ADMIN MENU**: If user is successfully authenticated, they are taken to this page. It gives user 5 options- Book Room, Bill Service, Current Bookings, Available Rooms, Exit. 

      ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/a9e419a1-9def-4303-a89a-6acb1bf4aca3)


      1. [**BOOK ROOM**](#bookrooms): refer to [1.1](#bookrooms) as same functionality and views take care of this option. 

      1. **BILL SERVICE**: If user click on the Bill Service they are brought to this page. It allows user to search a particular reservation using booking id. If not find an error is displayed otherwise all fields are updated with details form the found booking. Then the admin can add discount (5%, 10% or 25%) which will change the total/ night saving the info. The user can clear and or close the window as well from the corresponding buttons. 

         ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/3c387c5f-a49b-4c0a-bbae-2be29cb246c1)


      1. **Current Bookings**: If the user clicks the current bookings button on the admin view page they are brought to this page. It displays the number of bookings and a table view of all the bookings made along with details like name of guest, guest name, number of rooms, date and number of days. 

         ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/7d96c417-a707-4ee6-938f-e455f12cd39f)


      1. **Available Rooms**: If the user clicks the available rooms button on the admin view page they are brought to this page. It displays the number of available for booking and a table view of all the available rooms along with details like room id, room type and rate. ![A screenshot of a computer

      ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/64d53c48-c409-421b-8eb9-863bbd0a39e4)


      1. **View Guests**: Allows admin to view and search a list of all current and previous Guests in the resort. 

      ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/eebbfc42-2236-4a7d-8298-efeef0623f2c)

**CLASS DIAGRAM**

   ![image](https://github.com/hashmeet02/EmerladChasmResortKiosk/assets/103609995/b61f5410-303f-48e5-baf5-ce020c7724df)


