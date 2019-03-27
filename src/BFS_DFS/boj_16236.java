package BFS_DFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_16236 {

	static int N;
	static int[][] map;
	static boolean[][] visited;
	static int sharkX, sharkY;
	static int sharkSIZE = 2;
	static int eat = 0;
	static PriorityQueue<Node> q;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, -1, 0, 1 };
	static int time = 0;

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		q = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				// TODO Auto-generated method stub
				return 0;
			}
		});

		for (int i = 0; i < N; i++) {

			st = new StringTokenizer(br.readLine(), " ");

			for (int j = 0; j < N; j++) {

				int input = Integer.parseInt(st.nextToken());
				map[i][j] = input;
				if (input == 9) {
					sharkX = j;
					sharkY = i;
				}

			}

		}
		bfs();
		System.out.println(time);

	}

	static void bfs() {

		while (true) {
			Queue<Node> q = new LinkedList<>();
			ArrayList<Node> fish = new ArrayList<>();

			visited = new boolean[N][N];

			q.add(new Node(sharkX, sharkY, 0));
			visited[sharkY][sharkX] = true;

			int found = -1;

			while (!q.isEmpty()) {

				Node n = q.poll();
				int move = n.move;

				// ���� ����� �Ÿ� �� ���� �� �߰� !
				if (found == move) {
					break;
				}

				for (int i = 0; i < 4; i++) {

					int nextX = n.x + dx[i];
					int nextY = n.y + dy[i];

					if (nextX < 0 || nextY < 0 || nextX >= N || nextY >= N)
						continue;

					if (visited[nextY][nextX])
						continue;

					visited[nextY][nextX] = true;

					if (sharkSIZE >= map[nextY][nextX]) {

						if (map[nextY][nextX] > 0 && sharkSIZE > map[nextY][nextX]) {

							found = move + 1;
							fish.add(new Node(nextX, nextY, move + 1));
						}

						q.add(new Node(nextX, nextY, move + 1));

					}

				}

			}

			// ���̻� ������ ���� !
			if (found == -1) {
				break;
			} else {

				if (fish.size() > 1) {
					Collections.sort(fish, new ySort());
				}

			}

			Node fishNode = fish.get(0);

			if (found != -1) {

				time += found;
				eat += 1;
				map[sharkY][sharkX] = 0;

				sharkY = fishNode.y;
				sharkX = fishNode.x;
				map[sharkY][sharkX] = 9;

				if (sharkSIZE == eat) {
					sharkSIZE += 1;
					eat = 0;
				}

			}

		}

	}

	static class ySort implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub

			if (o1.y < o2.y) {
				return -1;
			} else if (o1.y == o2.y) {
				if (o1.x < o2.x) {
					return -1;
				} else if (o1.x == o2.x) {
					return 0;
				}
			}

			return 1;

		}

	}

	static class Node {

		int x, y, move;

		Node(int x, int y, int move) {
			this.x = x;
			this.y = y;
			this.move = move;
		}

	}

}