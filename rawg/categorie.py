import requests
from deep_translator import GoogleTranslator

CLE_API_RAWG = "4a0804a55b19456491e6678c762aa217"
EMAIL = "admin@gamelife.fr"
MOT_DE_PASSE = "Test123?!"
URL_AUTH = "http://localhost:8080/utilisateur/auth"
URL_ENDPOINT_BACK = "http://localhost:8080/api/v1/categories"

def obtenir_token():
    payload = {
        "email": EMAIL,
        "password": MOT_DE_PASSE
    }

    try:
        reponse = requests.post(URL_AUTH, json=payload)
        reponse.raise_for_status()
        return reponse.text
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP pour obtenir le token d'accès :", e)
        return None

def obtenir_categories():
    url = "https://api.rawg.io/api/genres"

    try:
        reponse = requests.get(url, params={"key": CLE_API_RAWG})
        reponse.raise_for_status()
        donnees = reponse.json()
        traducteur = GoogleTranslator(source='en', target='fr')
        categories = [{"libelle": traducteur.translate(categorie['name'])} for categorie in donnees['results']]
        return categories
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'API RAWG :", e)
        return []

def envoyer_categories_vers_backend(categorie, token):
    try:
        headers = {"Authorization": f"Bearer {token}"}
        reponse = requests.post(URL_ENDPOINT_BACK, json=categorie, headers=headers)
        reponse.raise_for_status()
        print("Catégorie envoyée avec succès à l'endpoint back-end.")
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'endpoint back-end :", e)

if __name__ == "__main__":
    token = obtenir_token()
    if token:
        categories = obtenir_categories()
        if categories:
            for categorie in categories:
                envoyer_categories_vers_backend(categorie, token)
        else:
            print("Aucune catégorie trouvée.")
    else:
        print("Impossible d'obtenir le token d'accès.")