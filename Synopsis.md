# Concept #

**musicbrainzws2-java** make query the MusicBrainz server as easy as:

```

artist.search("pink floyd");```

and

```

artist.getComplete(Id);```

We could stop here, but more, you could gain full controll on the type and amount of data each call will ask for, just setting some additional parameters like:

```

artist.Include.Releases(false);
artist.Include.Aliases(true);
and
artist.setLimit(100);
artist.setMinScore(40);```

You could search releases that match the CD in your drive:
```

releases = release.lookUp("D:");```

or a given tracklist, a toc descriptor or a discId:

```

releases = release.lookUp(tracklist);
releases = release.lookUp(toc);
releases = release.lookUp(discId);```

If you have a [Musicbrainz account](http://musicbrainz.org/register), you could store your release collections, rate your favourites Artist, Releases, Recordings, Works and Labels or add some tag to them:

```

release.addTags("tag1","tag2");
artist.rate(4);
collection.addRelease(releaseId1);
or
collection.removeReleases(releaseId);```

**DISCLAIMER**

The above is not realy the actual sintax, but is not so far, keep on reading!

## Controllers ##

In normal circumstance your application needs to operate with just five
'major' classes, mamed controllers:

```

Label, Artist, ReleaseGroup, Release, Recording, Work```

They are in charge to convert your requests in as few web service call as possible, hiding all the HTTP, XML, Encoding/Decoding and other stuff you don't need to worry about.

Calls to controllers methods could result in:

## Entities ##

The mains 'data store'. Contains all the data loaded by one or more requests to the relative controller

The Entity name convention is:

```

EntityName = ControllerName+Ws2 (es. ArtistWs2).```

## Search results ##

The members of Lists returned by controllers search method, are essentially composed by the corresponding Entity and the score.

Score is the ‘relevance degree' that the  MusicBrainz server gives to results, according to the Query clause submitted.

You could use score for filter and sort results.

The SearchResult name convention is:

```

SearchResult = ControllerName+Result+Ws2 (es. ArtistResultWs2).```