# gallery
A gallery app where where the user can browse images using a keyword. Type in your search term and click on enter.

Further images related to the term will be displayed in a staggered grid. I have used recycler view for the same. I have used Pixaby API (https://pixabay.com/api/docs/) to fetch images and related details. I have used Volley library to make network calls.

The default view is 2 column detail. The number of columns can be changed from 2 to 3 column and 4 column from the options menu. We can search images for a new term from the SearchView in the ActionBar.

The recycler view uses pagination to implement infinite scroll support. 50 items are fetched using one api request.

Whatever terms the user has searched as well as the associated search results will be persisted using Volley. So even if the user is offline he can still view results for already searched terms.
