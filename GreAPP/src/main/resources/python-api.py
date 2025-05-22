from flask import Flask, jsonify
from word_forms.word_forms import get_word_forms
from wordfreq import zipf_frequency
app = Flask(__name__)

@app.route('/get-word-forms/<word>', methods=['GET'])
def get_word_forms_api(word):
    try:
        word_forms = get_word_forms(word.lower())
        # Convert sets to lists for JSON serialization
        word_forms_serializable = {pos: list(forms) for pos, forms in word_forms.items()}
        return jsonify(word_forms_serializable)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route("/get-word-category/<word>",methods=['GET'])
def get_word_frequency(word):
	val=zipf_frequency(word,'en')
	if val>=6.0:
		return "very common"
	elif val>=5.0 and val<6.0:
		return "common"
	elif val>=4.0 and val<5:
		return "uncommon"
	elif val>=3.0 and val<4:
		return "rare"
	elif val<3:
		return "very rare"

		

if __name__ == '__main__':
    app.run(port=5000)
