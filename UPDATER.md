# The Choice Against The Updater

We have been working on a good updating experience for CXClient a lot and we
think our updater is really nice, but we still decided against it, here's why:

- “Everything's tied to a stupid webserver, that's gonna go obsolete in a few
  years”: If we run this updater on every shutdown (how it should be used), once
  our servers go offline, it will annoy you on every shutdown.
- “I won't be able to reminisce about 20 years from now”: If you _have_ to
  update, you won't be able to use the really old versions in a few years, which
  would be fatal retro-wise.

The two quotes are from Bryan Lunduke's talk
[“programmers_are_evil();”](https://youtu.be/_e6BKJPnb5o).

If the updater can be disabled (or is maybe even disabled by default), it might
be an option though.
