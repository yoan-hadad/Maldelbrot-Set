# Mandelbrot Fractal Explorer (Java Swing)

A small Java Swing app that renders the **Mandelbrot set** with adjustable colors/background, iteration depth, click-to-zoom, and an optional “draw animation” mode.

> **Challenge choice:** to push myself, I intentionally didn’t use the BufferedImage library. Instead, every redraw/zoom recomputes the fractal pixel-by-pixel using a more tedious zoom approach.

---

## Features
- Renders the **Mandelbrot set** in a Swing window
- Control **max iterations** (detail level)
- Choose **fractal color** and **background color**
- **Click-to-zoom** - each click zooms in by **×2** centered around the click(there is a known issue where the zoom feature breaks after x16)
- **Animation mode** - progressively increases iterations to show the fractal “Growing”

---

## How the fractal generation works (the logic)
For each screen pixel `(i, j)`, the program maps it to a complex number:

- \( c = x + yi \) (a point in the complex plane)

Then it iterates the classic Mandelbrot recurrence:

- \( z_{0} = 0 \)
- \( z_{n+1} = z_{n}^{2} + c \)

A point is considered to “escape” if:

- \( |z|^2 = \text{Re}(z)^2 + \text{Im}(z)^2 > 4 \)

The number of iterations before escaping determines the color:
- If it **never escapes** up to `MaxIterations`, the pixel is colored **black** (inside the set).
- Otherwise, it’s colored based on how fast it escapes using a smooth-ish coefficient:
  - `coeff = (iterations / MaxIterations) ^ 0.25`
  - final RGB = `baseRGB * coeff`

---

## Zoom approach
Instead of storing a pre-rendered image and zooming into it, each click:
1. Reads the mouse position (pixel coordinates)
2. Converts it into a complex-plane center point
3. Shrinks the visible complex-plane range using:
   - `range = 2.0 / zoom`
4. **Re-renders all pixels** for the new complex window

This is slower than buffering, but it makes the math and mapping very explicit.

---

## Controls
- **“Mandelbrot Set” button**: renders the fractal with the current settings
- **Click on the fractal**: zoom in by **×2**
- **Iterations slider**: increases detail (higher = slower)
- **RGB sliders**: controls the color used for escape shading
- **Background sliders / buttons**: change/reset UI + fractal background color
- **“Start Animation”**: draws frames with increasing iteration depth

---

## Requirements
- **Java JDK 8+** (recommended: 17+)
- No external libraries (pure Swing/AWT)

---

## Run the project

### Option 1: NetBeans (recommended)

1. **Create** a NetBeans project
2. **Replace** the contents of src folder with those of the zip
3. Open the project in **NetBeans**
4. Run `FractalWindow`

### Option 2: Any other Java IDE
1. **Create** a Java project
2. **Replace** the contents of src folder with those of the zip
3. Make sure the folder structure matches the package
4. Set the project SDK to the right Java version
5. Run `FractalPackage.FractalWindow`
