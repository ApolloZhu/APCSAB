__author__ = "Apollo/Zhiyu Zhu/朱智语"
__copyright__ = """
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
"""
__license__ = "MIT"
__email__ = "public-apollonian@outlook.com"

m = int(input("A small x: "))
n = int(input("A small y: "))

cache = [[[] for y in range(n + 1)] for x in range(m + 1)]


def count(x, y):
    if x == 0 or y == 0: return 1
    if cache[x - 1][y - 1]: return cache[x - 1][y - 1]
    cache[x - 1][y - 1] = count(x - 1, y) \
                          + count(x, y - 1) \
                          + count(x - 1, y - 1)
    return cache[x - 1][y - 1]


input("There are " + str(count(m, n)) + " ways to (" + str(m) + "," + str(n) + "), input anything to continue...")

ways = [[[] for y in range(n + 2)] for x in range(m + 2)]


def path(x, y):
    if x == 0 and y == 0: return {""}
    if ways[x][y]: return ways[x][y]
    if x > 0:
        for s in path(x - 1, y):
            ways[x][y].append(s + "E ")
    if y > 0:
        for s in path(x, y - 1):
            ways[x][y].append(s + "N ")
    if x > 0 and y > 0:
        for s in path(x - 1, y - 1):
            ways[x][y].append(s + "NE ")
    return ways[x][y]


for way in path(m, n): print(way)
