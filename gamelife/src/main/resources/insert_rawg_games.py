import requests
from bs4 import BeautifulSoup

RAWG_API_KEY = "fc6fc9da02854e66b8d2d0115d28c680"
EMAIL = "admin@gamelife.fr"
PASSWORD = "Test123?!"
URL_AUTH = "http://localhost:8080/utilisateur/auth"
URL_BACKEND_ENDPOINT = "http://localhost:8080/api/v1/games"

def get_token():
    payload = {
        "email": EMAIL,
        "password": PASSWORD
    }

    try:
        response = requests.post(URL_AUTH, json=payload)
        response.raise_for_status()
        return response.text
    except requests.exceptions.RequestException as e:
        print("Error during HTTP request to get access token:", e)
        return None

def strip_html_tags(text):
    soup = BeautifulSoup(text, "html.parser")
    return soup.get_text()

def get_game_details(game_id):
    url = f"https://api.rawg.io/api/games/{game_id}"

    try:
        response = requests.get(url, params={"key": RAWG_API_KEY})
        response.raise_for_status()
        game = response.json()

        platforms = [platform['platform']['name'] for platform in game['platforms']]
        genres = [genre['name'] for genre in game['genres']]
        
        # Fetch screenshots
        screenshots_url = f"https://api.rawg.io/api/games/{game_id}/screenshots"
        screenshots_response = requests.get(screenshots_url, params={"key": RAWG_API_KEY})
        screenshots_response.raise_for_status()
        screenshots = screenshots_response.json()
        images = [screenshot['image'] for screenshot in screenshots['results']]

        description = strip_html_tags(game['description'])

        return {"name": game['name'], "description": description, "genres": genres, "platforms": platforms, "images": images}
    except requests.exceptions.RequestException as e:
        print("Error during HTTP request to RAWG API:", e)
        return None

def send_game_to_backend(game, token):
    try:
        headers = {"Authorization": f"Bearer {token}"}
        response = requests.post(URL_BACKEND_ENDPOINT, json=game, headers=headers)
        response.raise_for_status()
        print("Game successfully sent to the backend endpoint.")
    except requests.exceptions.RequestException as e:
        print("Error during HTTP request to backend endpoint:", e)

if __name__ == "__main__":
    token = get_token()
    if token:
        for game_id in range(1, 2):
            game = get_game_details(game_id)
            if game and len(game['genres']) > 0 and len(game['platforms']) > 0:
                send_game_to_backend(game, token)
            else:
                print(f"Could not get details for game with ID {game_id}.")
    else:
        print("Unable to obtain access token.")