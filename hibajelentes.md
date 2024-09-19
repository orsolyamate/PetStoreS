# Nincs ellenőrzés a kötelező mezőkre

**Rövid leírás**: A kötelező mezők nincsenek validálva. Ha azok nincsenek kitöltve, akkor is elmenthető a pet objektum.

**Link**: https://petstore.swagger.io/#/pet/addPet

**Verzió**: 1.0.7 OAS 2.0```

**Lépések**

1. Nyissuk meg a https://petstore.swagger.io/ oldalt.
2. Kattintunk a "Post /pet Add a new pet to the store" fülre.
3. Kattintsunk a "Try it out" gombra.
4. módosítsuk a megjenet json-t, hogy a kötelező mezők ne legyenek kitöltve. (name, photoUrls)

   **Például:**
    ```
    {
        "id": 42,
        "category": {
            "id": 1,
            "name": "Pegazus"
        },
        "tags": [
            {
            "id": 2,
            "name": "fast"
            }
        ],
        "status": "available"
    }
    ```
5. Kattintsunk a "Execute" gombra.

**Várt eredmény:**

A pet objektum nem mentődik el, mert a kötelező mezők nincsenek kitöltve.

**Tényleges eredmény:**

A pet objektum elmentődik, pedig a kötelező mezők nincsenek kitöltve.

**Hibajelenség:** A pet objektum elmentődik, akkor is ha nincs kitöltve a "name"  és a "photoUrls" kötelező mezők.

**Prioritás:** Kritikus

**Név:**  Máté Orsolya
**dátum:** 2024.09.19.

