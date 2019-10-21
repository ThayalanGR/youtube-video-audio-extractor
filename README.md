# youtube-video-audio-extractor

> Java implementation of custom YouTube multiple video and audio file downloadable link extractor.


---

**process y2mate to grab audio link**

> get video id : input

```language
process : https://mate09.y2mate.com/analyze/ajax : link

formdata :-

    url:https://www.youtube.com/watch?v=jr3XJr4FCYk

    ajax:1

```

> get response : from above link

```language
process the response :

    get \_id and v_id
```

```language
process : https://mate09.y2mate.com/convert : link

body:-
    type: youtube
    \_id: \_id
    v_id: v_id
    ajax: 1
    ftype: mp3
    fquality: 128
```

> get response : from above link

```language
process the response:

get the result > grab link

http://dl74.y2mate.com/?file=M3R4SUNiN3JsOHJ6WWQ2a3NQS1Y5ZGlxVlZIOCtyZ3ZpdFErd3drY0FLVkl0SUltMDl1d0xOdEJFYWdkeEkyckg5OVl5alRLSXVpaElsdXZsYUV0REg2UnBKQTdzRFBLeko0aFRjRXNFMFRlZ3VtMmdpUjBqRUtuV05YZFJ2MEVTeTgrOFdaNmdTdVMyUFNhdWhyMStERG1yRitDYUNBVGpUb0ZPZWZmK0p0M3hXYlpZY2pyd1prQ3BDaWU3cVZEeUtPbG5oT2k1WkVIczVKNFRoY3ljSnhVM0pUaDlzQ1I5QkpPenN4TmxCWDE1N0R6VXRnd0JMV1hhekprTnk4TXRMNjZDMHBJbDNGTm9UUT0%3D\

```

> play audio using above link.
