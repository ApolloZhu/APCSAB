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
