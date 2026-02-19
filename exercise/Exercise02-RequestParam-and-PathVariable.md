# Exercise 02 - @RequestParam, @PathVariable

Create an Anime class inside the domain package with the fields long id and String name.
Implement a method in the Anime class that returns a "hardcoded" list of Anime;
Update the AnimeController to return this list. Finally, implement
two additional methods: one to filter by name using @RequestParam, and another
to return an Anime by its ID, using @PathVariable