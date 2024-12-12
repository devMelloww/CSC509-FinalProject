# Robot Simulation Program - README
Team Members: Damian Dhesi, Reza Mousakhani, Shiv Panchal

## Project Intro

The project uses Python to link image processing and robotic control, creating an automated system for drawing images. The process begins with a Python script that analyzes a given image, extracts key points or contours, and converts them into a set of coordinates. These coordinates represent the critical features of the image and are optimized to ensure smooth transitions between points. The resulting points are then passed to a collaborative robot (Cobot), which interprets these instructions and moves along a predefined static Z-plane to draw the image.

[Class Diagram (if PDF doesnt work)](https://lucid.app/lucidchart/f8b166a9-00d2-4449-a466-2a6bce295d21/edit?viewport_loc=-10020%2C-3784%2C16262%2C7076%2C0_0&invitationId=inv_4b2eb1b5-85fe-433c-b41a-2e7cbb61620f)

## Prerequisites

Ensure the following software and tools are installed on your system:

1. **Java Development Kit (JDK)**
    - Version: JDK 23
    - [Download JDK 23](https://www.oracle.com/java/technologies/javase-downloads.html)

2. **Integrated Development Environment (IDE)**
    - Recommended: IntelliJ IDEA
    - [Download IntelliJ IDEA](https://www.jetbrains.com/idea/)

3. **Docker Desktop**
    - [Download Docker Desktop](https://www.docker.com/products/docker-desktop/)

---

## Installation and Setup

### Step 1: Clone the GitHub Repository
1. Open your terminal or command prompt.
2. Navigate to the directory where you want to clone the repository.
   ```bash
   cd /path/to/your/directory
   ```
3. Clone the repository:
   ```bash
   git clone <repository_url>
   ```

### Step 2: Open the Project in IntelliJ IDEA
1. Launch IntelliJ IDEA.
2. Select **Open** and navigate to the directory where the repository was cloned.
3. Click **OK** to open the project.

### Step 3: Start the Docker Container
1. Open Docker Desktop and ensure it is running.
2. Open your terminal.
3. Run the following command to start the robot simulation container:
   ```bash
   docker run --rm -it -e ROBOT_MODEL=UR3e -p 5900:5900 -p 30001:30001 -p 30002:30002 -p 30004:30004 -p 6080:6080 --name ur3e_container universalrobots/ursim_e-series
   ```

### Step 4: Connect to the Robot Simulation
1. Open your browser and go to:
   ```
   http://localhost:6080/vnc.html
   ```
2. Click **Connect** to connect with the robot simulation.

---

## Running the Program

1. In IntelliJ IDEA, run the program from the **Run** menu or by pressing `Shift + F10`.
2. Once the program starts:
    - Click **File** in the menu bar.
    - Select **Upload** and choose an image file to upload.
    - The outline of the image will appear.
3. To start the simulation:
    - Click **Simulation** in the menu bar.
    - Select **Start Simulation**.
4. A pop-up message will confirm the connection: `You have connected`.
5. Go back to your browser on `http://localhost:6080/vnc.html` to observe the simulation.

---

## Notes

- Ensure Docker Desktop is running before executing the Docker command.
- The simulation requires an active internet connection to fetch the Docker image if not already available locally.
- For any issues, consult the repository's documentation or contact the developer.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

