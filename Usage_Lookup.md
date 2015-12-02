# Lookup #

According to [MusicBrainz XML Web Service/Version 2 definition](http://musicbrainz.org/doc/XML_Web_Service/Version_2#Lookups) you could perform lookUps by an entity whenever you know his ID or, in some special case, [other identifiers ](http://musicbrainz.org/doc/XML_Web_Service/Version_2#Non-MBID_Lookups).

In **musicbrainzws2-java** You could perform LookUps with the Id or the Entity instance you get, as an example, in searchResult from searches.

[Non MBID Lookups](Usage_NonMBIDLookup#NonMBIDLookup.md) are handeld via searches (Isrc, Iswc) or additional controllers(DiscId and Puid).

Lookups in [MusicBrainz XML Web Service/Version 2 definition](http://musicbrainz.org/doc/XML_Web_Service/Version_2#Lookups) are limited in terms of 'type' of information and 'quantity' of related entity could retrieve (always 25). If you need more or different, you have to perform [Browses](http://musicbrainz.org/doc/XML_Web_Service/Version_2#Browse) instead.

**musicbrainzws2-java** let you don’t mind about, just ask for the information you need and you'll get it in the fewer API call as possible.

As a programmer, you could find the way to force **musicbrainzws2-java** to perform browse instead of lookups or viceversa, or even browse instead of searches (could be very useful in many situations, as for hit a non-MBID Lookup for instance). Have a look to the controllers code and org.musicbrainz.queryWs2.browse package if interested in how to.

As searches, LookUp have a preloaded sets of 'defaults' includes, filters and other parameters, that you could override at runtime, once you instantiate the controller and before the execution of the query.

To perform a LookUp:

```

Controller controller = new Controller();
entity = controller.lookUp(entity);```

or if you prefer to use the Id:

```

controller = new Controller();
entity = controller.lookUp(entity.getId());```

### Example ###
```

(see: org.muscibrainz.junit.unitTests.synopsisUseCase4)

Artist artist = new Artist();

artist.getSearchFilter().setLimit((long)30);
artist.getSearchFilter().setMinScore((long)50);

artist.search("pink floyd");
List<ArtistResultWs2> results  =  artist.getFirstSearchResultPage();

if (results.isEmpty()) return;

ArtistWs2 pf = results.get(0).getArtist();

artist = new Artist();
ArtistWs2 pinkFloyd= artist.lookUp(pf);

artist = new Artist();
pinkFloyd= artist.lookUp(pf.getId());```

In both case, this will perform a LookUp using default parameters. If you need to change something before (the connection parameters as an example), you have to use this other form:

```

Controller controller = new Controller();
...
controller.setQueryWs(myWebServiceImplementation);
...
entity = controller.lookUp(entity);
or
entity = controller.lookUp(entity.getId());```

### Subqueries ###

Lookup only load informations directly related to the Entity, as detail fields, aliases and relations,  [subqueries](http://musicbrainz.org/doc/XML_Web_Service/Version_2#Subqueries) are not executed via lookup call.

If you want to execute also all the subqueries at the first call, You could  call the getComplete() method:

```

Controller controller = new Controller();
entity = controller.getComplete(entity);
or
entity = controller.getComplete(entity.getId());```
Sometime is useful hit a Lookup to get a first set of data, then execute subqueries to load the remainder.

To do this, each Controller expose a set of methods to handle subqueries:

```

controller.hasMore+RelatedController+s();
controller.getFull+RelatedController+List();
controller.getFirst+RelatedController+ListPage();
controller.getNext+RelatedController+ListPage();```

where you have to replace +RelatedController+ whit the appropriate controller's name (i.e. hasMoreLabels() and getFullLabelList() for Label).

They operate as you can expect:

controller.hasMore+RelatedController+s();

is true when the controlled entity has more RelatedEntity istance to ask for (or it never tried to yet);

controller.getFull+RelatedController+List();

return a full List of RelatedEntity (i.e. LabelWs2), NOT a SearchResult.

Note that as a conseguence hasMore+RelatedController+s turn to false.

and so on.

### Example ###
```
(see: org.muscibrainz.junit.unitTests.synopsisUseCase5)

String artistName = "pink floyd";

Artist artist = new Artist();
artist.search(artistName);
List<ArtistResultWs2> results  =  artist.getFullSearchResultList();

... some safety test here...

ArtistWs2 pinkFloyd = results.get(0).getArtist();
artist = new Artist();

pinkFloyd = artist.getComplete(pinkFloyd);

OR

pinkFloyd = artist.lookUp(pinkFloyd);
...
you could get results in a List if you want:

List<ReleaseGroupWs2> rgl = artist.getFullReleaseGroupList();
...

paginate:

artist.getFirstReleaseListPage();
...
while (artist.hasMoreReleases())
{
artist.getNextReleaseListPage();
...
}

or simply store the results in pinkFloyd;

artist.getFullReleaseVAList();
artist.getFullRecordingList();
artist.getFullWorkList();

at the end, get it:

pinkFloyd = artist.getComplete(pinkFloyd);
or
pinkFloyd = artist.lookUp(pinkFloyd); ```

note they are equivalent at this point.


### Subquery Filters ###

For any subquery you could set filters:

### Example ###
```
(see: org.muscibrainz.junit.unitTests.synopsisUseCase6)

To get Only Releases by type "Album" and status "Official"

artist.getReleaseBrowseFilter().getReleaseTypeFilter().setTypeAlbum(true);
artist.getReleaseBrowseFilter().getReleaseStatusFilter().setStatusOfficial(true);

To get 50 releases per page:

artist.getReleaseBrowseFilter().setLimit((long)50);

pinkFloyd = artist.lookUp(pinkFloyd);
artist.getFullReleaseList();```

To change the 'Type' of information Lookup and Subqueries returns, you could give different values to the
[includes parameters](http://musicbrainz.org/doc/XML_Web_Service/Version_2#inc.3D_arguments_which_affect_subqueries).

According [MusicBrainz XML Web Service/Version 2 definition](http://musicbrainz.org/doc/XML_Web_Service/Version_2#Subqueries), every Entity could present some 'linked' details as aliases, relations, linked entity and even relations to linked entity (for releases).

**musicbrainzws2-java** support all the inc definitions available for LookUp, regardless it uses a LookUp, a Browse or a Search to retrieve the info, plus a little bit more (i.e. annotations).

### Includes ###

To Ask the controller change the set of information to load via LookUp, you could call:
```

controller.getIncludes().set+Parameter+(value);```

### Example ###
```

artist.getIncludes().setAliases(true);```

Note that you could ask for different sets of info in subsequent call of the LookUp method:

### Example ###
```

(see: org.muscibrainz.junit.unitTests.synopsisUseCase7)


artist.getIncludes().setAliases(false);
artist.getIncludes().setArtistRelations(false);
artist.getIncludes().setWorkRelations(false);
artist.getIncludes().setAnnotation(false);

artist.LookUp(pinkFloyd);

will load only the ReleaseRelations, UrlRelations and ReleaseGroupRelations.

then, when needed, you could add the others:

artist.getIncludes().setAliases(true);
artist.LookUp(pinkFloyd);

artist.getIncludes().setAnnotation(true);
artist.LookUp(pinkFloyd);

artist.getIncludes().setArtistRelations(true);
artist.getIncludes().setWorkRelations(true);
artist.LookUp(pinkFloyd);```

same for


```
artist.getComplete(pinkFloyd); ```

unless it will try to execute subqueries too.

to inform  controller.getComplete() on which subquery execute, you could use the appropriate

```

controller.getIncludes().set+Parameter+(value); ```

### Example ###
```
(see: org.muscibrainz.junit.unitTests.synopsisUseCase8)

artist.geIncludes().setReleaseGroups(false);
artist.getIncludes().setReleases(false);
artist.getIncludes().setRecordings(false);
artist.getIncludes().setVariousArtists(false);
artist.getIncludes().setWorks(false);

artist.getComplete(pinkFloyd);

will perform exactly as

artist.LookUp(pinkFloyd);```

again, You could set to true the appropriates parameter first, then
hit as many subsequent

```

artist.getComplete(pinkFloyd);```

you need.

Includes are awaillable also for subqueries:

controller.get+RelatedController+Includes().set+Parameter+(value);

### Example ###
```
(see: org.muscibrainz.junit.unitTests.synopsisUseCase9)

artist = new Artist();

artist.getIncludes().setAliases(false);
artist.getIncludes().setArtistRelations(false);
artist.getIncludes().setWorkRelations(false);
artist.getIncludes().setAnnotation(false);

artist.getIncludes().setReleaseGroups(false);
artist.getIncludes().setReleases(false);
artist.getIncludes().setRecordings(false);
artist.getIncludes().setVariousArtists(false);
artist.getIncludes().setWorks(false);

artist.getComplete(pinkFloyd);

// is a simple LookUp with no Incs.

artist.getFirstWorkListPage();

// get the first page and turn on the Include param for works

artist.getComplete(pinkFloyd);

// will complete ONLY the work list.

artist.getFirstReleaseListPage();

artist.getReleaseIncludes().setLabel(false);
artist.getReleaseIncludes().setRecordings(true);
artist.getReleaseIncludes().setArtistRelations(true);

artist.getComplete(pinkFloyd);

// will complete the release list, but be careful...
// only the last results reports Recordings and
// ArtistRelations!!!```