import sys
import cv2
import json


def image_processing(image_path):
    # Load the image
    image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    if image is None:
        raise ValueError("Image not found or cannot be loaded.")

    # Detect edges
    edges = cv2.Canny(image, 100, 200)

    # Find all contours
    contours, _ = cv2.findContours(edges, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    return contours, image.shape


def save_contours_to_json(contours, filename):
    # Convert contours to a serializable format (list of lists)
    contours_list = [contour.squeeze().tolist() for contour in contours if contour.size > 0]

    # Save to JSON
    with open(filename, 'w') as json_file:
        json.dump(contours_list, json_file, indent=4)

    print(f"Contours saved to {filename}")


def main():
    if len(sys.argv) < 2:
        print("Usage: python main.py <image_path>")
        return

    image_path = sys.argv[1]

    try:
        contours, shape = image_processing(image_path)
        save_contours_to_json(contours, filename='contours.json')
    except Exception as e:
        print(f"Error: {e}")


if __name__ == '__main__':
    main()