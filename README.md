# Best Matched Restaurants Search Engine

## Focus of this project

- Java Project using Spring Boot to facilitate packaging and running in standalone mode.
- Added 2 types of execution:
    - API (default)
    - CLI 
- This project has no UI
- The response of the search is returned formatted as a JSON

## Usage
First, you need to decide what type of execution you want to run. Below is the description of how to run in both modes (API or CLI):

### Run in CLI mode:
1. In the same directory as the JAR file:
    ```bash
    java -jar best-matched-restaurants-epk.jar --cli --spring.main.web-application-type=none
    ```
2. This will start the application in CLI mode. After it's running, you just have to follow the questions that will show in the terminal regarding the parameters of the search.
   1. You can skip any parameters by pressing ENTER
   2. Only parameters that are informed are used in the search
   3. If you inform any invalid parameter, it'll show an Error message and ask if you want to do another search
3. The result of the search is exhibited formatted as JSON in the terminal
4. After the execution, the program will ask if you want to do another search

### Run in API mode:
1. In the same directory as the JAR file:
    ```bash
    java -jar best-matched-restaurants-epk.jar
    ```
2. This will start the application in API mode (port 8080). After it's running, you can send requests to http://localhost:8080/api/restaurants/search using curl command or a API Request Client (like Bruno or Postman). Here's an example below:
    ```bash
    curl --request GET \
    --url http://localhost:8080/api/restaurants/search \
    --header 'content-type: application/json' \
    --data '{
    "name": "deli",
    "rating": 3,
    "distance": 2,
    "price": 30,
    "cuisine": "Chinese"
    }'
    ```
3. The search parameters are handed over through the body of the request, formatted as JSON. You can specify all search parameters in the body, or choose which parameter you want to send. Here are a few examples different body parameters:
     ```bash
    {
    "name": "deli",
    "rating": 3,
    "distance": 2,
    "price": 30,
    "cuisine": "Chinese"
    }
    ```
    ```bash
   {
   "name": "deli",
   "rating": 3
   }
    ```
4. If you need to kill the Spring application process running in port 8080, you can run the following command (be careful as this will kill all processes running in 8080):

    MacOS / Linux: 
   ```bash
    lsof -t -i:8080 | xargs kill -9
    ```
    Windows: 
   ```bash
    netstat -ano | findstr :8080
    ```
    ```bash
    taskkill /PID 12345 /F
    ```

## Assumptions

- Searches of names of restaurants and cuisines are case-insensitive
- If the name or rating is invalid in the csv, it will still show in the resulted search (this doesn't apply because the csv contains only correct values)
- (CLI) If the user gives an invalid parameter (e.g. Rating < 1 or Rating > 5) it will show an ERROR message, but still ask if the user wants to make another search
- All search results will return all the values / columns from the Restaurant (Name, Customer Rating, Distance, Price, Cuisine Name) formatted as a JSON

## Technical Explanation of the Application

Steps:
1. Entry point:
   1. If you run in CLI mode, the entry point is the `RestaurantSearchCLI` class.
      1. For CLI execution, the `RestaurantSearchCriteria` object will be instantiated during execution after the program asks the inputs from the user.
   2. If you run in API mode, the entry point is the `RestaurantSearchController` class, through the endpoint `api/restaurants/search`
      1. For API execution, the `RestaurantSearchCriteria` object will be received from the body of the request, that is handed as a JSON.
2. Both entry points call the search engine `RestaurantSearchService` passing the `RestaurantSearchCriteria` as the user input.
3. All Restaurants and Cuisines are loaded from CSV files (`CuisineCsvLoader` / `RestaurantCsvLoader` & `RestaurantConverter`) as dependencies inside `RestaurantSearchService`
4. The Restaurants are all loaded as objects of the `Restaurant` class in a `List<Restaurant>` inside the `RestaurantSearchService`
5. The `RestaurantSearchService` call the validation of inputs through `RestaurantSearchValidator` and after that, it does all the search filters and sorting using the predefined rules (using Collections framework in Java).
6. The returned list of `Restaurant` that fall into the search is converted into a list of `RestaurantResponse` for better visualizing in JSON format. 

## Usability Explanation of the Application

In this project, there's data about local restaurants located near a central location, which is found in the restaurants.csv file. This project contains a basic search function that allows anyone to search those restaurants to help them find where they would like to have lunch. The search is based on five criteria: Restaurant Name, Customer Rating(1 star ~ 5 stars), Distance(1 mile ~ 10 miles), Price(how much one person will spend on average, USD 10 ~ USD 50), Cuisine(Chinese, American, Thai, etc.).

The function allow users to provide up to five parameters based on the criteria listed above.
If parameter values are invalid, it returns an error message.
The function return up to five matches based on the provided criteria. If no matches are found, it returns an empty list. If less than 5 matches are found, it returns them all. If more than 5 matches are found, it returns the best 5 matches. The returned results are sorted according to the rules explained below. Every record in the search results contains at least the restaurant name.

“Best match” is defined as below:
- A Restaurant Name match is defined as an exact or partial String match with what users provided. For example, “Mcd” would match “Mcdonald’s”.
- A Customer Rating match is defined as a Customer Rating equal to or more than what users have asked for. For example, “3” would match all the 3 stars restaurants plus all the 4 stars and 5 stars restaurants.
- A Distance match is defined as a Distance equal to or less than what users have asked for. For example, “2” would match any distance that is equal to or less than 2 miles.
- A Price match is defined as a Price equal to or less than what users have asked for. For example, “15” would match any price that is equal to or less than USD 15 per person.
- A Cuisine match is defined as an exact or partial String match with what users provided. For example, “Chi” would match “Chinese”. All the possible Cuisines are found in the cuisines.csv file.

The five parameters are holding an “AND” relationship. For example, if users provide Name = “Mcdonald’s” and Distance = 2, this should find all “Mcdonald’s” within 2 miles.

When multiple matches are found, they are sorted as described below.
- Sort the restaurants by Distance first.
- After the above process, if two matches are still equal, then the restaurant with a higher customer rating wins.
- After the above process, if two matches are still equal, then the restaurant with a lower price wins.
- After the above process, if two matches are still equal, then the order is random.

Example: if the input is Customer Rating = 3 and Price = 15. Mcdonald’s is 4 stars with an average spend = USD 10, and it is 1 mile away. And KFC is 3 stars with an average spend = USD 8, and it is 1 mile away. Then we should consider Mcdonald’s as a better match than KFC. (They both matches the search criteria -> we compare distance -> we get a tie -> we then compare customer rating -> Mcdonald’s wins)
