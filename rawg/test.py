import requests
from deep_translator import GoogleTranslator

CLE_API_RAWG = "e467a49d9d454b34a89a2afe569adaf4"
EMAIL = "admin@gamelife.fr"
MOT_DE_PASSE = "Test123?!"
URL_AUTH = "http://localhost:8080/utilisateur/auth"
URL_ENDPOINT_BACK = "http://localhost:8080/api/produits"

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

def obtenir_details_jeu(id):
    url = f"https://api.rawg.io/api/games/{id}"

    try:
        reponse = requests.get(url, params={"key": CLE_API_RAWG})
        reponse.raise_for_status()
        jeu = reponse.json()
        return jeu['name']   
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'API RAWG :", e)
        return None

def envoyer_jeu_vers_backend(jeu, token):
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
        for id in range(1,21):
            jeu = obtenir_details_jeu(id)
            print(jeu)
    else:
        print("Impossible d'obtenir le token d'accès.")
