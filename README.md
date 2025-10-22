# Best Matched Restaurants Search Engine

This is ...

## Focus of this project

- Java Project using Spring Boot to facilitate packaging and running in standalone mode.
- Added 2 types of execution:
  - CLI (default)
  - API
- This project has no UI
- The response of the search is returned formatted as a JSON

## Usage

1. Clone the repository (remove):
    ```bash
    git clone https://github.com/edupklein/best-matched-restaurants-epk.git
    ```

## Assumptions

- Searches of names of restaurants and cuisines are case-insensitive
- If the name or rating is invalid in the csv, it will still show in the resulted search (this doesn't applied because the csv contains only correct values)
- If the user gives an invalid parameter (e.g. Rating < 1 or Rating > 5) it will show an ERROR message, but still ask if the user wants to make another search
- All search results will return all the values / columns from the Restaurant (Name, Customer Rating, Distance, Price, Cuisine Name)

## Technical Explanation of the Application

Steps:
1. All Restaurants and Cuisines are loaded from CSV files (CuisineCsvLoader / RestaurantCsvLoader & RestaurantConverter)
2. 

The core of the Restaurant Search Engine is inside the RestaurantSearchService class (inside 'search' method).

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
