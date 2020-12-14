# naamakirja-springboot

REST-kutsut, joilla ohjelmaa pystyy käyttämään (Esim. VSCode + [REST Plugin](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) Toimii kivasti.

### HAE KAIKKI KÄYTTÄJÄTILIT ###
GET https://nixon-naamakirja.herokuapp.com/rest/accounts

### LUO UUSI KÄYTTÄJÄTILI ###
POST https://nixon-naamakirja.herokuapp.com/rest/registration
Content-Type: application/json

{
    "username": "testi4",
    "visibleName": "Testi nelonen",
    "password": "salasana",
    "friendRequests": [],
    "friends": []
}

### LÄHETÄ KAVERIPYYNTÖ KÄYTTÄJÄLTÄ KAVERILLE ###
### FROM: {Kkäyttäjän Id}, TO: {kaverikäyttäjän id}
POST https://nixon-naamakirja.herokuapp.com/rest/friendRequest/send/from/1/to/6
Content-Type: application/json

### HYVÄKSY KAVERIPYYNTÖ KAVERIEHDOKKAALTA ###
### FROM: {Kkäyttäjän Id}, TO: {kaverikäyttäjän id}
POST https://nixon-naamakirja.herokuapp.com/rest/friendRequest/accept/from/2/to/1
Content-Type: application/json

### KATSO KAIKKI KAVERISUHTEET ###
### Näyttää tulokset friendship-taulusta, jossa lähinnä ID-arvoja ###
GET https://nixon-naamakirja.herokuapp.com/rest/friendships

### NÄYTÄ KÄYTTÄJÄN SAAMAT KAVERIPYYNNÖT ###
### am. esimerkissä käyttäjän id on 1 ###
GET https://nixon-naamakirja.herokuapp.com/rest/friendships/requests/received/1
Content-Type: application/json

### NÄYTÄ KÄYTTÄJÄN LÄHETTÄMÄN KAVERIPYYNNÖT ###
### am. esimerkissä käyttäjän id on 1 ###
GET https://nixon-naamakirja.herokuapp.com/rest/friendships/requests/sent/1
Content-Type: application/json

### NÄYTÄ HYVÄKSYTYT KAVERIPYYNNÖT (A.K.A KAVERIT) ###
### am. esimerkissä käyttäjän id on 1 ###
GET https://nixon-naamakirja.herokuapp.com/rest/friendships/accepted/1
Content-Type: application/json

### NÄYTÄ KAIKKI MAHDOLLISET POSTAUKSET ###
GET https://nixon-naamakirja.herokuapp.com/rest/posts
Content-Type: application/json

### NÄYTÄ KÄYTTÄJÄN KAIKKI POSTAUKSET ###
### am. esimerkissä käyttäjän id on 1 ###
GET https://nixon-naamakirja.herokuapp.com/rest/posts/1
Content-Type: application/json

### LUO KÄYTTÄJÄLLE UUSI POSTAUS ###
### am. esimerkissä käyttäjän id on 1 ###
POST https://nixon-naamakirja.herokuapp.com/rest/post/1
Content-Type: application/json

{
    "postContent": "Fragmentit, tyylittely, javascript, kuvatiedostot ja testaus puuttuu..."
}
