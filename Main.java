package net.ukr.andy777;

/*
 Lesson06.3
 Напишите многопоточный вариант сортировки массива методом Шелла.
 P.S. реалізовано вибір з кількох варіантів сортування (0-Bubble, 1-Insert, 2-Shell, 3-Shell2). кількість потоків задається. 
 */

import java.util.Arrays;

public class Main {

	// реалізовані методи сортування
	static String[] sortMethods = { "Bubble", "Insert", "Shell", "Shell2" };

	public static void main(String[] args) {
		int[] array = new int[50000]; // ініціалізація масиву
		for (int i = 0; i < array.length; i++)
			array[i] = (int) (Math.random() * 10); // наповнення масиву випадковими числами 0-9

		// сортування системним методом Arrays.sort (для порівняння)
		int[] array1 = resSystemSort(array.clone());

		// запуск кількох циклів сортувань різними method-методами та різною кількістю threads-потоків
		for (int method = 0; method <= 3; method++)
			// номер методу. наразі - від 0 до 3
			for (int threads = 1; threads <= 4; threads++)
				// кількість потоків
				resMultiThreadSort(array.clone(), threads, method, array1);
	}

	// метод запуску сортування системним методом + підрахунок часу виконання
	public static int[] resSystemSort(int array[]) {
		long tstart = System.currentTimeMillis();
		Arrays.sort(array);
		long tend = System.currentTimeMillis();
		printArray(array, array);
		System.out.println((tend - tstart) + " ms" + " - SYSTEM Arrays.sort");
		return array;
	}

	// метод запуску сортування заданим method-методом та заданою кількістю threads-потоків array-массиву + підрахунок
	// часу виконання
	public static void resMultiThreadSort(int array[], int qThreads, int sortMethod, int arrTrue[]) {
		long tstart = System.currentTimeMillis();
		MultiThreadSorting.sort(array, qThreads, sortMethod);
		long tend = System.currentTimeMillis();
		printArray(array, arrTrue);
		System.out.println((tend - tstart) + " ms" + " = " + qThreads + "-thread sort " + sortMethods[sortMethod]
				+ "-method");
	}

	// метод друку елементві array-масиву та результату співпадання його елементів з еталонним arrTrue-масивом
	public static void printArray(int array[], int arrTrue[]) {
		boolean res = true;
		for (int i = 0; i < array.length; i++) {
			// System.out.print(array[i]); // вивід кожного елемента масиву
			if (array[i] != arrTrue[i]) {
				res = false;
			}
		}
		System.out.print(" - " + res + "-Sort \t"); // вивід результату співпадання всіх елементів двох масивів
	}
}