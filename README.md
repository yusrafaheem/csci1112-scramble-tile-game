# Scramble Tile Game (CSCI 1112 HW01)

A Java implementation of a Scrabble-like tile game, built for GWU's CSCI 1112 course. The assignment provides the game scaffolding (dictionary loading, a demo driver, random number generation, unit tests) and asks students to implement the core game logic in `TileGame.java`.

## What I wrote

- **TileGame.java** — the core game logic: tile point values, computing a word's score, drawing a random hand of tiles from the tile bag, and searching the dictionary for the best-scoring word playable from a given hand.

- ## Provided scaffolding (not my code)

- - **Scramble.java** — a demo driver that runs a multi-round, multi-player game using `TileGame`.
  - - **WordTool.java** — loads and cleans the dictionary from the `words` file.
    - - **UniformRandom.java** — a seeded uniform random number generator (Rahul Simha).
      - - **Utilities.java** — small string-cleaning helper.
        - - **UnitTests.java** — the course's test harness for validating `TileGame`.
          - - **words** — dictionary file used by `WordTool`.
           
            - ## Running it
           
            - ```
              javac *.java
              java Scramble       # play a demo game
              java UnitTests      # run the test suite
              ```
