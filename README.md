# Construction Material Shop Management Backend
## Overview
Project is used to report final project of subject Object oriented analysis and design, UIT.<br>
* **Start time:** Nov 26th, 2023<br>
* **End time:** Mar 8th, 2024


<p align="center">
  Figure 1. Dashboard
  <img src="https://github.com/vinguyen1907/Backend-Construction-Material-Shop/blob/main/src/main/docs/captures/dashboard.png" alt="Dashboard">
</p>

<p align="center">
  Figure 2. Orders
  <img src="https://github.com/vinguyen1907/Backend-Construction-Material-Shop/blob/main/src/main/docs/captures/orders.png" alt="Orders">
</p>

<p align="center">
  Figure 3. Order details
  <img src="https://github.com/vinguyen1907/Backend-Construction-Material-Shop/blob/main/src/main/docs/captures/order_details.png" alt="Order details">
</p>

## System architecture
![alt text](https://github.com/vinguyen1907/Backend-Construction-Material-Shop/blob/main/src/main/docs/system_architecture.jpeg)

## Diagrams
<p align="center">
  Figure 4. Class diagram
  <img src="https://github.com/vinguyen1907/Backend-Construction-Material-Shop/blob/main/src/main/docs/class_diagram.png" alt="Class diagram">
</p>
<p>
    <p align="center" >
      Figure 5. Database diagram
      <img src="https://github.com/vinguyen1907/Backend-Construction-Material-Shop/blob/main/src/main/docs/database_diagram.png" alt="Database diagram">
    </p>
</p>

### Other diagrams (use case, sequence, activity) you can find here: [Diagrams](https://github.com/vinguyen1907/Backend-Construction-Material-Shop/tree/main/src/main/docs/diagrams)


## Setup Guide
Follow these steps to set up Docker for this project

### Step 1: Clone this project
```bash
git clone https://github.com/vinguyen1907/Backend-Construction-Material-Shop
```

### Step 2: Install project
#### 1. Open project's directory in terminal
#### 2. Clean and Install to generate .jar file
```bash
mvn clean install
```
**_Note_**: Ensure that you have installed Maven on your device

### Step 3: Set up Docker for MySQL
#### 1. Pull MySQL Image
```bash
docker pull mysql:latest
```

#### 2. Run MySQL Container
```bash
docker run \
    --name your_mysql_container \
    -e MYSQL_ROOT_PASSWORD=your_root_password \
    -e MYSQL_DATABASE=your_database \
    -e MYSQL_USER=your_user \
    -e MYSQL_PASSWORD=your_password \
    -p host_port:container_port \
    -d \
    mysql:latest
```
* _your_mysql_container_: Replace with the desired name for your MySQL container.
* _your_root_password_: Replace with the desired root password for MySQL.
* _your_database_: Replace with the name of the initial MySQL database.
* _your_user_: Replace with the username for a MySQL user.
* _your_password_: Replace with the password for the MySQL user.
* _host_port:container_port_: Map a port from the host to the container for MySQL. Replace host_port and container_port with the desired values.

**_Note_**: If you use root user, please remove MYSQL_USER and MYSQL_PASSWORD, add MYSQL_ALLOW_EMPTY_PASSWORD=true

### Step 4: Set up Docker for Spring boot
#### 1. Build Docker image
```bash
docker build -t your_image_name .
```

#### 2. Create network
```bash
docker network create <network-name>
```
Check if creating network successfully
```bash
docker network ls
```

#### 3. Run Docker container
```bash
docker run \
    --name your_container_name \
    --network your_network_name \
    -p host_port:container_port \
    -e ENV_VARIABLE=value \
    -v /host/path:/container/path \
    -d \
    your_image_name
```
* _your_container_name_: Specify the desired name for your container.
* _your_network_name_: Specify the name of the Docker network you want to connect your container to.
* _host_port:container_port_: Map a port from the host to the container. Replace host_port and container_port with the desired values.
* -e ENV_VARIABLE=value: Set environment variables inside the container. Replace ENV_VARIABLE with the name of the environment variable and value with its value.
* -v /host/path:/container/path: Mount a volume from the host to the container. Replace /host/path with the path on the host and /container/path with the path in the container.
* -d: Run the container in the background (detached mode).
* _your_docker_image_: Specify the Docker image you want to use.

# Other 
* Frontend repository: https://github.com/dkdeptrai/Frontend_Construction_Material_Shop
