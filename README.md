# Route finding application

This application calculates the shortest path in an undirected unweighted graph using dijkstra's algorithm.
 
Country data is loaded from the json file and stored in memory at the application startup. 

The calculation is reachable via rest service generated from documentation in resources/api.yaml file.
 
You can run the application and call the service accordingly: 

http://{host}:{port}/routing/{target}/{source}

example 

http://localhost:8080/routing/SVK/PRT

