from flask import Flask, jsonify
from word_forms.word_forms import get_word_forms
import os
import re

app = Flask(__name__)

@app.route('/get-words-from-file', methods=['GET'])
def getWordsFromFile():
    try:
        base_path = os.path.dirname(__file__)
        file_path = os.path.join(base_path, 'show.txt')

        # Read from the text file
        with open(file_path, "r", encoding="utf-8") as file:
            input_text = file.read()

        # Regex pattern to extract word, definition, and example
        pattern = r'\d+\.\s+\*\*(.*?)\*\* – (.*?)\s+_([^_]+)_'

        # Extract matches
        matches = re.findall(pattern, input_text, re.DOTALL)

        # Format into list of dictionaries
        word_list = [
            {
                "word": word.strip(),
                "definition": definition.strip(),
                "example": example.strip()
            }
            for word, definition, example in matches
        ]

        return word_list

    except FileNotFoundError:
        return jsonify({'error': 'File not found'}), 404
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(port=5000)
