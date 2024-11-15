// fetch("https://mepro.pearson.com/CallToServer.aspx/PostActivityLogDetails", {
//   "headers": {
//     "accept": "*/*",
//     "accept-language": "en-US,en;q=0.9",
//     "authorization": "CMW-TOKEN ;V&tvqdDwP9{b^@&d$58&&f0uAr>@b}qar!~IQ&b}&9UZ<me7d",
//     "content-type": "application/json",
//     "sec-ch-ua": "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"",
//     "sec-ch-ua-mobile": "?1",
//     "sec-ch-ua-platform": "\"Android\"",
//     "sec-fetch-dest": "empty",
//     "sec-fetch-mode": "cors",
//     "sec-fetch-site": "same-origin",
//     "x-requested-with": "XMLHttpRequest"
//   },
//   "referrer": "https://mepro.pearson.com/my-tasks",
//   "referrerPolicy": "strict-origin-when-cross-origin",
//   "body": "{\"obj\":{\"ActivityScore\":100,\"ActivityStatus\":\"completed\",\"TimeSpent\":\"00:10:00\",\"ActivityScoreDetailsList\":[]}}",
//   "method": "POST",
//   "mode": "cors",
//   "credentials": "include"
// });

#include <iostream>
#include <windows.h>
#include <winhttp.h>
#include <thread>
#include <chrono>

#pragma comment(lib, "winhttp.lib")

void makeApiRequest() {
    // Initialize WinHTTP session
    HINTERNET hSession = WinHttpOpen(L"WinHTTP Example/1.0",
                                     WINHTTP_ACCESS_TYPE_DEFAULT_PROXY,
                                     WINHTTP_NO_PROXY_NAME,
                                     WINHTTP_NO_PROXY_BYPASS, 0);

    if (hSession) {
        // Connect to the server
        HINTERNET hConnect = WinHttpConnect(hSession, L"mepro.pearson.com", INTERNET_DEFAULT_HTTPS_PORT, 0);

        if (hConnect) {
            // Create the request
            HINTERNET hRequest = WinHttpOpenRequest(hConnect, L"POST", L"/CallToServer.aspx/PostActivityLogDetails",
                                                    NULL, WINHTTP_NO_REFERER,
                                                    WINHTTP_DEFAULT_ACCEPT_TYPES,
                                                    WINHTTP_FLAG_SECURE);

            if (hRequest) {
                // Set headers
                WinHttpAddRequestHeaders(hRequest, L"Acasacept: */*", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"Accept-Language: en-US,en;q=0.9", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"Authorization: CMW-TOKEN ;V&tvqdDwP9{b^@&d$58&&f0uAr>@b}qar!~IQ&b}&9UZ<me7d", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"Content-Type: application/json", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"sec-ch-ua: \"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"sec-ch-ua-mobile: ?1", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"sec-ch-ua-platform: \"Android\"", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"sec-fetch-dest: empty", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"sec-fetch-mode: cors", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"sec-fetch-site: same-origin", -1L, WINHTTP_ADDREQ_FLAG_ADD);
                WinHttpAddRequestHeaders(hRequest, L"x-requested-with: XMLHttpRequest", -1L, WINHTTP_ADDREQ_FLAG_ADD);

                // Set JSON body
                const wchar_t* body = L"sdkjhdakj";
                DWORD bodySize = wcslen(body) * sizeof(wchar_t);

                // Send the request
                BOOL requestSent = WinHttpSendRequest(hRequest,
                                                      WINHTTP_NO_ADDITIONAL_HEADERS, 0,
                                                      (LPVOID)body, bodySize,
                                                      bodySize, 0);

                // Receive the response
                if (requestSent && WinHttpReceiveResponse(hRequest, NULL)) {
                    DWORD dwSize = 0;
                    DWORD dwDownloaded = 0;
                    char buffer[1024];

                    // Print the response
                    do {
                        dwSize = sizeof(buffer) - 1;
                        ZeroMemory(buffer, sizeof(buffer));

                        if (WinHttpQueryDataAvailable(hRequest, &dwSize) && dwSize > 0) {
                            if (WinHttpReadData(hRequest, buffer, dwSize, &dwDownloaded)) {
                                buffer[dwDownloaded] = '\0';
                                std::cout << buffer << std::endl;
                            }
                        }
                    } while (dwSize > 0);
                } else {
                    std::cerr << "Failed to send request or receive response" << std::endl;
                }

                // Close request handle
                WinHttpCloseHandle(hRequest);
            }

            // Close connection handle
            WinHttpCloseHandle(hConnect);
        }

        // Close session handle
        WinHttpCloseHandle(hSession);
    }
}

int main() {
    while (true) {
        makeApiRequest();  // Call the API function
        std::this_thread::sleep_for(std::chrono::seconds(2));  // Wait for 2 seconds
    }

    return 0;
}
