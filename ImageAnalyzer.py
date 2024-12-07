import sys
import cv2
import json
import numpy as np


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
#     contours_list = [contour.squeeze().tolist() for contour in contours if contour.size > 0]

#     # Save to JSON
#     with open(filename, 'w') as json_file:
#         json.dump(contours_list, json_file, indent=4)

    # change back to json format if time permits
    with open(filename, 'w') as file:
        for contour in contours:
            if contour.size > 0:
                point_list = contour.squeeze().tolist()
                for point in point_list:
                    if isinstance(point, int):
                        file.write("0,{}\n".format(point))
                    else:
                        x, y = point
                        file.write("{},{}\n".format(x,y))

    print(f"Contours saved to {filename}")


def tracing_image(contours, shape):
    # Create a blank image of the same dimensions as the input image
    height, width = shape
    blank_image = np.zeros((height, width, 3), dtype=np.uint8)

    # Draw all contours
    for contour in contours:
        cv2.drawContours(blank_image, [contour], -1, (255, 255, 255), 1)  # White lines for the contours

    # Encode the image as PNG
    success, encoded_image = cv2.imencode('.png', blank_image)
    if success:
        # Save the encoded image to a file
        with open('traced_image.png', 'wb') as file:
            file.write(encoded_image)
        print("Image saved as 'traced_image.png'")
    else:
        print("Failed to encode the image.")


def main():
    if len(sys.argv) < 2:
        print("Usage: python main.py <image_path>")
        return

    image_path = sys.argv[1]

    try:
        contours, shape = image_processing(image_path)
        tracing_image(contours, shape)
        save_contours_to_json(contours, filename='contours.txt')
    except Exception as e:
        print(f"Error: {e}")


if __name__ == '__main__':
    main()