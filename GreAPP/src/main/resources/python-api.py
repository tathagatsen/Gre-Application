from flask import Flask, jsonify
from word_forms.word_forms import get_word_forms

app = Flask(__name__)

@app.route('/get-word-forms/<word>', methods=['GET'])
def get_word_forms_api(word):
    try:
        word_forms = get_word_forms(word)
        # Convert sets to lists for JSON serialization
        word_forms_serializable = {pos: list(forms) for pos, forms in word_forms.items()}
        return jsonify(word_forms_serializable)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(port=5000)
