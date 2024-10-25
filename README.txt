Angry Birds Game Clone
Overview
This project is a clone of the popular Angry Birds game developed using LibGDX. The game involves screen till now wihch includes different levels, menu, win, pause etc screens 

Contributors
Bhavesh : built win lose menu, rendering of level, readme file building
Harshil : complete initial setup, build birds pig classes, helped in designing of the sprites, interface of level builiding

Setup Instructions
Follow these steps to set up the project after downloading it from GitHub:

Download the Repository

Clone the repository using the following command:
bash
Copy code
git clone https://github.com/harshil-0/ap-angry.git
Install LibGDX

Ensure you have LibGDX installed and set up in your development environment.
Import Project

Open your IDE (e.g., IntelliJ IDEA, Eclipse) and import the project as a Gradle project.
Set Up Assets

Ensure all necessary asset files (images, audio) are placed in the android/assets directory.
Download images referenced in the code from Angry Birds Wiki and add them to the assets folder.
Build the Project

Build the project using the Gradle tasks provided by your IDE.
Run the Project

Once the project is built successfully, run the DesktopLauncher class to start the game.

OOPs Concepts used :
Inheritance:

The Pig class serves as an abstract base class that can be extended by specific pig types.
Abstract Classes:

The Pig class is abstract, defining common behaviors (takeDamage, move) while allowing subclasses to implement specific features.
Interfaces:

Access Modifiers:

Public: Methods and classes that need to be accessed from outside the package, such as takeDamage and move in the Pig class.
Private: Fields like size, position, and health in the Pig class are marked as private to encapsulate the data, ensuring they can only be modified through public methods.

References

Images used in the project are sourced from the Angry Birds Wiki.
Additional references and resources can be found in the project documentation.

THANKS !!  :)