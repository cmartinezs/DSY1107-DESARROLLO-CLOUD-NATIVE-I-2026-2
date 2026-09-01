const apiBaseUrl = import.meta.env.VITE_API_BASE_URL as string;

export async function apiGet<T>(path: string, accessToken: string): Promise<T> {
  const response = await fetch(`${apiBaseUrl}${path}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
      Accept: 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error(`API respondió HTTP ${response.status}`);
  }

  return response.json() as Promise<T>;
}
