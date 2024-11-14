Backend - IslandGameBack

This backend project is built with Java Spring Boot and powers the data processing for the Island Game. It retrieves, parses, and structures map data for gameplay on the frontend.
Project Overview

The backend is designed to:
    Retrieve map data from an API endpoint (https://jobfair.nordeus.com/jf24-fullstack-challenge/test).
    Parse the matrix data into structured information, distinguishing between land and water cells based on height values (0 for water, greater than 0 for land).
    Identify and convert islands by grouping connected land cells and calculating each island’s average height.

Tech Stack
    Java Spring Boot: API requests, data parsing, and backend logic.
    REST API: Provides structured data for frontend display and game logic.
    Docker: Easily deployable via Docker with a docker-compose.yml file that sets up the backend alongside the frontend.
