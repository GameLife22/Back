import requests
from deep_translator import GoogleTranslator

CLE_API_RAWG = "4a0804a55b19456491e6678c762aa217"
EMAIL = "admin@gamelife.fr"
MOT_DE_PASSE = "Test123?!"
URL_AUTH = "http://localhost:8080/utilisateur/auth"
URL_ENDPOINT_BACK = "http://localhost:8080/api/v1/produits"

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

def obtenir_jeux():
    url = "https://api.rawg.io/api/games"

    try:
        reponse = requests.get(url, params={"key": CLE_API_RAWG})
        reponse.raise_for_status()
        donnees = reponse.json()
        jeux = []
        for jeu in donnees['results']:
            plateformes = [{"libelle": plateforme['platform']['name']} for plateforme in jeu['platforms']]
            traducteur = GoogleTranslator(source='en', target='fr')
            genres = [{"libelle": traducteur.translate(genre['name'])} for genre in jeu['genres']]
            images = [{"image": image['image'], "titre": jeu['name']} for image in jeu['short_screenshots']]
            jeux.append({"nom": jeu['name'], "description": "test", "categories": genres, "plateformes": plateformes, "images": images})
        return jeux
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'API RAWG :", e)
        return []

def envoyer_jeux_vers_backend(jeu, token):
    try:
        headers = {"Authorization": f"Bearer {token}"}
        reponse = requests.post(URL_ENDPOINT_BACK, json=jeu, headers=headers)
        reponse.raise_for_status()
        print("Jeu envoyé avec succès à l'endpoint back-end.")
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'endpoint back-end :", e)

if __name__ == "__main__":
    token = obtenir_token()
    if token:
        jeux = obtenir_jeux()
        if jeux:
            for jeu in jeux:
                envoyer_jeux_vers_backend(jeu, token)
        else:
            print("Aucun jeu trouvé.")
    else:
        print("Impossible d'obtenir le token d'accès.")