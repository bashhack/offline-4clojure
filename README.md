# offline-4clojure

An updated version of the excellent [Offline-4Clojure](https://github.com/thattommyhall/offline-4clojure) repository from [thattommyhall](https://github.com/thattommyhall), supporting Clojure 1.10.

This project aims to provide the curious Clojurian with an opportunity to have a mirror of the renowned [4Clojure](http://www.4clojure.com/) problem set in an offline-first format.

## Usage

To fetch problems from the 4Clojure website, run the following command:

```bash
$ lein run
```

To run tests against a problem, follow the command structure provided to invoke `lein run` for that namespace:

```bash
$ lein run -m offline-4clojure.p<num>
```

Or, more specifically - to run problem 21, one would execute:

```bash
$ lein run -m offline-4lojure.p21
```

## Links

Useful resources while working through the problems:

* [Leiningen](https://leiningen.org/)
* [Clojure Cheatsheet](https://clojure.org/api/cheatsheet)
* [Clojure Docs](https://clojuredocs.org/)

## License

Copyright © 2019

Distributed under the Eclipse Public License, the same as Clojure.
